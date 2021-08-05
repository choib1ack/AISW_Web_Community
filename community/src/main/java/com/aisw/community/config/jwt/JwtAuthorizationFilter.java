package com.aisw.community.config.jwt;

import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.repository.user.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;

// 인가
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


    private RedisUtil redisUtil;
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, RedisUtil redisUtil) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.redisUtil = redisUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String refreshToken = null;
        String refreshUname = null;

        String header = request.getHeader(JwtProperties.ACCESS_HEADER);
        System.out.println("header Authorization : " + header);

        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(JwtProperties.ACCESS_HEADER).replace(JwtProperties.TOKEN_PREFIX, "");
        System.out.println("token : " + token);
        // 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
        // 내가 SecurityContext에 집적 접근해서 세션을 만들때 자동으로 UserDetailsService에 있는
        // loadByUsername이 호출됨.

        try {
            String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                    .getClaim("username").asString();

            System.out.println(username);

            // 서명 정상
            if (username != null) {
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

            String refreshHeader = request.getHeader(JwtProperties.REFRESH_HEADER);
            System.out.println("refreshHeader Authorization : " + refreshHeader);

            if (refreshHeader == null || !refreshHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
                chain.doFilter(request, response);
                return;
            }
            refreshToken = request.getHeader(JwtProperties.REFRESH_HEADER).replace(JwtProperties.TOKEN_PREFIX, "");
            System.out.println("refreshToken : " + refreshToken);

        } catch (Exception e) {

        }
        try {
            if (refreshToken != null) {
                refreshUname = redisUtil.getData(refreshToken);
                System.out.println(refreshUname);
                String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(refreshToken)
                        .getClaim("username").asString();

                if (refreshUname.equals(username)) {
                    User user = userRepository.findByUsername(username);
                    PrincipalDetails principalDetails = new PrincipalDetails(user);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, // 나중에 컨트롤러에서 DI해서
                            // 쓸 때 사용하기 편함.
                            null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                            principalDetails.getAuthorities());

                    // 강제로 시큐리티의 세션에 접근하여 값 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    String newtoken = MakeAccessToken(principalDetails);
                    response.addHeader(JwtProperties.ACCESS_HEADER, JwtProperties.TOKEN_PREFIX + newtoken);
                }
            }
        } catch (TokenExpiredException e) {

        }

        chain.doFilter(request, response);
    }

    protected String MakeAccessToken(PrincipalDetails principalDetails) {
        return JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME))
//                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

    }
}
