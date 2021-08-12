package com.aisw.community.config.jwt;

import com.aisw.community.advice.exception.TokenException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.provider.JwtTokenProvider;
import com.aisw.community.provider.RedisProvider;
import com.aisw.community.repository.user.UserRepository;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 인가
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    private JwtTokenProvider jwtTokenProvider;

    private RedisProvider redisProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, RedisProvider redisProvider) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisProvider = redisProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // access token
        String header = request.getHeader(JwtProperties.ACCESS_HEADER_STRING);
        System.out.println("header Authorization : " + header);

        // header가 없거나 access token prefix가 잘못된 경우
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        // access token 확인
        String accessToken = jwtTokenProvider.resolveToken(request);
        System.out.println("accessToken: " + accessToken);
        // 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
        // 내가 SecurityContext에 직접 접근해서 세션을 만들때 자동으로 UserDetailsService에 있는
        // loadByUsername이 호출됨.

        String refreshToken = null;
        try {
            // 서명 정상
            // access token 검증 (서명, 만료)
            if (jwtTokenProvider.validateToken(accessToken)) {
                String username = jwtTokenProvider.requireDecodedJwt(accessToken).getClaim("username").asString();
                System.out.println("username: " + username);

                User user = userRepository.findByUsername(username);

                // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
                // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
                PrincipalDetails principalDetails = new PrincipalDetails(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, // 나중에 컨트롤러에서 DI해서
                        // 쓸 때 사용하기 편함.
                        null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                        principalDetails.getAuthorities());

                // 강제로 시큐리티의 세션에 접근하여 값 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (TokenExpiredException e) {
            // access token이 만료된 경우
            header = request.getHeader(JwtProperties.REFRESH_HEADER_STRING);
            System.out.println("refresh token header: " + header);
            // access token이 만료되어서 프론트로 exception
            if (header == null) {

                System.out.println(accessToken);
                throw new TokenException("access token", accessToken);
            }
            System.out.println("hello");
            refreshToken = header.replace(JwtProperties.TOKEN_PREFIX, "");
            System.out.println("refreshToken: " + refreshToken);
        } catch (JWTVerificationException e) {
            throw new TokenException("invalid token", accessToken);
        }

        try {
            // access token 만료 + refresh token 검증
            if (refreshToken != null) {
                // redis 내의 username과 refresh token으로 가져온 username 비교
                String refreshUname = redisProvider.getData(refreshToken);
                System.out.println(refreshUname);

                String username = jwtTokenProvider.requireDecodedJwt(refreshToken).getClaim("username").asString();

                if (refreshUname.equals(username)) {
                    User user = userRepository.findByUsername(username);
                    PrincipalDetails principalDetails = new PrincipalDetails(user);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, // 나중에 컨트롤러에서 DI해서
                            // 쓸 때 사용하기 편함.
                            null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                            principalDetails.getAuthorities());

                    // 강제로 시큐리티의 세션에 접근하여 값 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // access token 생성
                    String jwtToken = jwtTokenProvider.createToken(authentication, JwtProperties.EXPIRATION_TIME);
                    response.addHeader(JwtProperties.ACCESS_HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
                }
            }
        } catch (TokenExpiredException e) {
            throw new TokenException("refresh token", refreshToken);
        } catch (JWTVerificationException e) {
            throw new TokenException("invalid token", refreshToken);
        }


        chain.doFilter(request, response);
    }
}
