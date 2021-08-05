package com.aisw.community.config.jwt;

public interface JwtProperties {
    String SECRET = "AISW"; // 우리 서버만 알고 있는 비밀값
    long ACCESS_EXPIRATION_TIME = 1; // 10분 // 10일 (1/1000초) 864000000
    long REFRESH_EXPIRATION_TIME = 600000L * 60 * 24 * 10;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    String ACCESS_HEADER = "AccessToken";
    String REFRESH_HEADER = "RefreshToken";
}
