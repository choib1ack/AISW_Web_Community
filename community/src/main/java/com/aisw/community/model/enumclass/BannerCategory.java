package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BannerCategory {
    BANNER(0, "배너", "배너 광고"),
    INFORMATION(1, "정보", "유용한 정보");

    private Integer id;
    private String title;
    private String description;
}
