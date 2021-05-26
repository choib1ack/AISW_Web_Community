package com.aisw.community.advice.exception;

import lombok.Getter;

@Getter
public class AdminNotFoundException extends RuntimeException {

    private Long id;

    public AdminNotFoundException() {
    }

    public AdminNotFoundException(Long id) {
        this.id = id;
    }
}
