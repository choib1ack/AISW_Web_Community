package com.aisw.community.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentApiRequest {

    private Long id;

    private String content;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    private Long boardId;

    private Long userId;

    private Long superCommentId;
}
