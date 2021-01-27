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

    private Long isAnonymous;

    private Long likes;

    private Long freeId;
}
