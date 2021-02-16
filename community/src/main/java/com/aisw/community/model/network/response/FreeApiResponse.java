package com.aisw.community.model.network.response;

import com.aisw.community.model.enumclass.BulletinStatus;
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

    private String content;

    private String writer;

    private String attachmentFile;

    private BulletinStatus status;

    private Long views;

    private Long likes;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    // 학교 공지 0
    private Long level;

    private Long userId;

    private Long boardId;
}
