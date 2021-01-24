package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Campus {
    COMMON(0, "공통", "공통"),
    GLOBAL(1, "글로벌", "글로벌 캠퍼스"),
    MEDICAL(2, "메디컬", "메디컬 캠퍼스");

    private Integer id;
    private String title;
    private String description;
}
