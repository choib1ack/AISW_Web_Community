package com.aisw.community.advice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AttachmentStorageException extends RuntimeException{

    public AttachmentStorageException(String msg){
        super(msg);
    }

    public AttachmentStorageException(String msg, Throwable cause){
        super(msg, cause);
    }
}

