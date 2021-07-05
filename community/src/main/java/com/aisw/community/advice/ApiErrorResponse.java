package com.aisw.community.advice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorResponse {

    private String error;
    private String message;

    public ApiErrorResponse(String error, String message) {
        super();
        this.error = error;
        this.message = message;
    }
}
