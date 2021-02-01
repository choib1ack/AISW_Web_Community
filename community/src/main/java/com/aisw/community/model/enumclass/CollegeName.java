package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CollegeName {
    BUSINESS_ADMINISTRATION(0, "경영대학"),
    SOCIAL_SCIENCE(1, "사회과학대학"),
    HUMANITIES(2, "인문대학"),
    LAW(3, "법과대학"),
    ENGINEERING(4, "공과대학"),
    BIO_NANO(5, "바이오나노대학"),
    IT_CONVERGENCE(6, "IT 융합대학"),
    ORIENTAL_MEDICINE(7, "한의과대학"),
    ARTS_PHYSICAL(8, "예술/체육대학");

    private Integer id;
    private String title;
}
