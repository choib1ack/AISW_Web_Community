package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Grade {
    FRESHMAN(0, "1학년"),
    SOPHOMORE(1, "2학년"),
    JUNIOR(2, "3학년"),
    SENIOR(3, "4학년");

    private Integer id;
    private String title;
}
