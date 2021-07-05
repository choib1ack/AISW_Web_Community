package com.aisw.community.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UploadCategory {
    POST(0, "post", "게시판 첨부파일"),
    BANNER(1, "banner", "배너 이미지"),
    SITE(2, "site", "유용한 사이트 이미지");

    private Integer id;
    private String title;
    private String description;
}
