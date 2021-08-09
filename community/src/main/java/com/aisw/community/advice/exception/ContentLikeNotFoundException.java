package com.aisw.community.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContentLikeNotFoundException extends RuntimeException {

    private Long id;

    private Long userId;

    private Long targetId;

    public ContentLikeNotFoundException(Long id) {
        this.id = id;
    }

    public ContentLikeNotFoundException(Long userId, Long targetId) {
        this.userId = userId;
        this.targetId = targetId;
    }
}
