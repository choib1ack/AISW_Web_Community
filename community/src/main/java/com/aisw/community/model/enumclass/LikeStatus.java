package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LikeStatus {
    PRESSED(0, "좋아요 클릭", "좋아요 클릭"),
    UNPRESSED(1, "좋아요 취소", "좋아요 취소");

    private Integer id;
    private String title;
    private String description;
}
