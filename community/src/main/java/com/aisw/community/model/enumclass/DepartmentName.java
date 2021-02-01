package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DepartmentName {
    SOFTWARE(0, "소프트웨어학과"),
    AI(1, "인공지능학과");

    private Integer id;
    private String title;
}
