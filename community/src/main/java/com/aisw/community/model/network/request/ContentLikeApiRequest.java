package com.aisw.community.model.network.request;

import com.aisw.community.model.enumclass.LikeStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentLikeApiRequest {

    private Long id;

    private LikeStatus likeStatus;

    private Long accountId;

    private Long boardId;

    private Long commentId;
}
