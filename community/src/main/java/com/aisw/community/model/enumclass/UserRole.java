package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    NOT_PERMITTED("ROLE_GUEST", "미가입"),
    STUDENT("ROLE_STUDENT", "재학생"),
    COUNCIL("ROLE_COUNCIL", "학생회"),
    FACULTY("ROLE_FACULTY", "교수 및 조교"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
