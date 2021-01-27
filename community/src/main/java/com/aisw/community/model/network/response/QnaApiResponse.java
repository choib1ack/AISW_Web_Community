package com.aisw.community.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaApiResponse {

    private Long id;

    private String title;

    private String content;

    private String attachmentFile;

    private Long views;

    private Long likes;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    // 학교 공지 0
    private Long level;

    private String subject;

    private Long boardId;
}
