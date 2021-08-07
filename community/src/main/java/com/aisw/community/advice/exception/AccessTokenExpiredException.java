package com.aisw.community.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccessTokenExpiredException extends RuntimeException {

    private String msg;
    public AccessTokenExpiredException(String token){
        super(token);
        this.msg = "access token is expired: ";
    }


}
