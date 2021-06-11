package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BannerSubCategory {
    CODINGTEST(0, "코딩테스트", "코딩테스트 준비"),
    LECTURE(1, "강의", "온라인 강의"),
    RECRUITMENT(2, "채용", "채용 정보"),
    ACTIVITY(3, "대외활동", "공모전 및 대외활동 정보");

    private Integer id;
    private String title;
    private String description;
}
