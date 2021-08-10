package com.aisw.community.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenExpiredException extends RuntimeException {

    private String msg;

    public RefreshTokenExpiredException(String token, String msg) {
        super(token);
        this.msg = msg;
    }
}
