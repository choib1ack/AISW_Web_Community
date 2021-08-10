package com.aisw.community.advice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccessTokenExpiredException extends RuntimeException {

    private String msg;
    public AccessTokenExpiredException(String token,String msg){
        super(token);
        this.msg = msg;
    }


}
