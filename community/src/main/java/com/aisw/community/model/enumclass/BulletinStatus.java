package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BulletinStatus {
    URGENT(0, "긴급", "긴급 게시글"),
    TOP(1, "상단", "상단 고정 게시글"),
    GENERAL(2, "일반", "일반 게시글");

    private Integer id;
    private String title;
    private String description;
}
