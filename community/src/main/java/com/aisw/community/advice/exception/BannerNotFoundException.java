package com.aisw.community.advice.exception;

import lombok.Getter;

@Getter
public class BannerNotFoundException extends RuntimeException {

    private Long id;

    public BannerNotFoundException() {
    }

    public BannerNotFoundException(Long id) {
        this.id = id;
    }
}
