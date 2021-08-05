package com.aisw.community.provider;

import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.config.jwt.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    // JWT 토큰 생성
    public String createToken(Authentication authentication, long expiredTime) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        return JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiredTime))
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(JwtProperties.ACCESS_HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
    }

    public DecodedJWT requireDecodedJwt(HttpServletRequest request) {
        return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(resolveToken(request));
    }

    public DecodedJWT requireDecodedJwt(String token) {
        return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) throws TokenExpiredException, InvalidClaimException {
        return requireDecodedJwt(token).getExpiresAt().after(new Date());
    }
}
