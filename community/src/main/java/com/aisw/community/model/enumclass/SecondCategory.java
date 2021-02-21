package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SecondCategory {
    UNIVERSITY(0, "학교", "학교 공지글"),
    DEPARTMENT(1, "학과", "학과 공지글"),
    COUNCIL(2, "학생회", "학생회 공지글"),
    FREE(3, "자유게시판", "자유게시판 게시글"),
    QNA(4, "질문게시판", "질문게시판 게시글");

    private Integer id;
    private String title;
    private String description;
}
