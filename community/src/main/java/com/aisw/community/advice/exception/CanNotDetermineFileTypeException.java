package com.aisw.community.advice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CanNotDetermineFileTypeException extends RuntimeException {

    public CanNotDetermineFileTypeException(String msg) {
        super(msg);
    }

    public CanNotDetermineFileTypeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
