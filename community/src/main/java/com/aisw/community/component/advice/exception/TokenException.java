package com.aisw.community.component.advice.exception;

import com.auth0.jwt.exceptions.InvalidClaimException;
import lombok.Getter;

@Getter
public class TokenException extends InvalidClaimException {

    String token;

    public TokenException(String message, String token) {
        super(message);
        this.token = token;
    }


}
