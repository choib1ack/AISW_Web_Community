package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MALE(0, "남자"),
    FEMALE(1, "여자");

    private Integer id;
    private String title;

}
