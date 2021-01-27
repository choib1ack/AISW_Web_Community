package com.aisw.community.model.enumclass;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Job {
    STUDENT(0, "학생", "학생"),
    PROFESSOR(1, "교수", "교수"),
    FACULTY(2, "교직원", "교직원");

    private Integer id;
    private String title;
    private String description;

}
