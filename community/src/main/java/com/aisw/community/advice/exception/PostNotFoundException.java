package com.aisw.community.advice.exception;

import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException {

    private Long id;

    public PostNotFoundException() {
    }

    public PostNotFoundException(Long id) {
        this.id = id;
    }
}
