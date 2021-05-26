package com.aisw.community.advice.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private Long id;

    public UserNotFoundException() {
    }

    public UserNotFoundException(Long id) {
        this.id = id;
    }
}
