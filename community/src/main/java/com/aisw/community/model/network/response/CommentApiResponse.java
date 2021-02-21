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
public class CommentApiResponse {

    private Long id;

    private String writer;

    private String content;

    private LocalDateTime createdAt;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    private Long likes;

    private Long boardId;
}
