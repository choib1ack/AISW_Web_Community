package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AlertCategory {
    COMMENT(0, "댓글", "게시글 댓글"),
    NESTED_COMMENT(1, "대댓글", "게시글 대댓글"),
    LIKE_POST(2, "게시글 좋아요", "게시글 좋아요"),
    LIKE_COMMENT(3, "댓글 좋아요", "댓글 좋아요");

    private Integer id;
    private String title;
    private String description;
}
