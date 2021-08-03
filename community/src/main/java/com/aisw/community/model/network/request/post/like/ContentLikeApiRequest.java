package com.aisw.community.model.network.request.post.like;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentLikeApiRequest {

    private Long userId;

    private Long boardId;

    private Long commentId;
}
