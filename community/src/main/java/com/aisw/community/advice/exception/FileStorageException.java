package com.aisw.community.advice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileStorageException extends RuntimeException {

    public FileStorageException(String msg) {
        super(msg);
    }

    public FileStorageException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

