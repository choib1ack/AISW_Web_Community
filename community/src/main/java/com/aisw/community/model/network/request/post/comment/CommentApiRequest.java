package com.aisw.community.model.network.request.post.comment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentApiRequest {

    private Long id;

    private String content;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    private Long boardId;

    private Long superCommentId;
}
