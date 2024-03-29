package com.aisw.community.config.jwt;

import com.aisw.community.model.network.request.user.LoginRequest;
import com.aisw.community.component.provider.JwtTokenProvider;
import com.aisw.community.component.provider.RedisProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

// login 요청해서 post로 username, password 전송하면 UsernamePasswordAuthenticationFilter 동작
// formLogin을 disable하면 UsernamePasswordAuthenticationFilter 동작 안 함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // login해야하기 때문에 AuthenticationManager가 있어야 UsernamePasswordAuthenticationFilter로 작동 가능
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisProvider redisProvider;

    // Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
    // 인증 요청시에 실행되는 함수 => /login
    // 인증 안 되면 401 에러 / error: Unathorized
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // 1. username, password 받아서
        // 2. 정상인지 로그인 시도 -> AuthenticationManager로 로그인 시도하면
        // PrincipalDetailsService가 호출 -> loadUserByUsername() 함수 실행
        // 3. PricipalDetails를 세션에 담고 (권한 확인을 위함)
        // 4. JWT 토큰을 만들어서 리스폰스

        System.out.println("JwtAuthenticationFilter : 진입");

        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
        ObjectMapper om = new ObjectMapper();
        LoginRequest loginRequest = null;
        try {
            loginRequest = om.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 유저네임패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword());


        System.out.println("JwtAuthenticationFilter : 토큰생성완료");

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // authentication 객체가 session 영역에 저장됨 -> 로그인 완료
        return authentication;
    }

    // attemptAuthentication 실행 후 인증이 정상적이면 successfulAuthentication 실행
    // JWT Token 생성해서 response에 담아주기
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) {
        System.out.println("login success");
        String jwtToken = jwtTokenProvider.createToken(authResult, JwtProperties.EXPIRATION_TIME);
        System.out.println("login token: " + jwtToken);
        response.addHeader(JwtProperties.ACCESS_HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
        String refreshToken = jwtTokenProvider.createToken(authResult, JwtProperties.REFRESH_EXPIRATION_TIME);
        redisProvider.setDataExpire(authResult.getName(), refreshToken, JwtProperties.REFRESH_EXPIRATION_TIME);
        response.addHeader(JwtProperties.REFRESH_HEADER_STRING, JwtProperties.TOKEN_PREFIX + refreshToken);
    }
}
