package com.aisw.community.advice.exception;

import lombok.Getter;

@Getter
public class CommentNotFoundException extends RuntimeException {

    private Long id;

    public CommentNotFoundException() {
    }

    public CommentNotFoundException(Long id) {
        this.id = id;
    }
}
