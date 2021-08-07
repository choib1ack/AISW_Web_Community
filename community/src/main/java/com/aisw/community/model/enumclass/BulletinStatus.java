package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BulletinStatus {
    URGENT(0, "긴급", "긴급 게시글"),
    NOTICE(1, "공지", "공지 게시글"),
    REVIEW(2, "후기", "취업후기 게시글"),
    GENERAL(3, "일반", "일반 게시글");

    private Integer id;
    private String title;
    private String description;
}
