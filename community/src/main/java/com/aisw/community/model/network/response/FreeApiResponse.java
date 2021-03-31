package com.aisw.community.model.network.response;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreeApiResponse {

    private Long id;

    private String title;

    private String writer;

    private String content;

    private BulletinStatus status;

    private Long views;

    private Long likes;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    // 학교 공지 0
    private Long level;

    private SecondCategory category;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private Long accountId;
}
