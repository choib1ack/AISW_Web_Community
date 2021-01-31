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
public class FreeCommentApiResponse {

    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private String createdBy;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    private Long likes;

    private Long freeId;
}
