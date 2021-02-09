package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BoardCategory {
    FREE(0, "자유게시판", "자유게시판 게시글"),
    QNA(1, "질문게시판", "질문게시판 게시글");

    private Integer id;
    private String title;
    private String description;
}
