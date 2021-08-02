package com.aisw.community.config.jwt;

public interface JwtProperties {
	String SECRET = "AISW"; // 우리 서버만 알고 있는 비밀값
	int EXPIRATION_TIME = 600000 * 10; // 10분 // 10일 (1/1000초) 864000000
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
}