package com.aisw.community.model.network.response.post.like;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ContentLikeApiResponse {

    private Long id;

    private Long userId;

    private Long boardId;

    private Long commentId;
}
