package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FirstCategory {
    NOTICE(0, "공지", "공지"),
    BOARD(1, "게시판", "게시판");

    private Integer id;
    private String title;
    private String description;
}
