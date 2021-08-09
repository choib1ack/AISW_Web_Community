package com.aisw.community.advice;

import com.aisw.community.advice.exception.AccessTokenExpiredException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiErrorResponse {

    private int status;
    private String error;
    private String message;

    public ApiErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public ApiErrorResponse(HttpStatus badRequest, String error, AccessTokenExpiredException ex) {
        this.status = badRequest.value();
        this.error = error;
        this.message = ex.getMsg() + ex.getMessage();
    }
}
