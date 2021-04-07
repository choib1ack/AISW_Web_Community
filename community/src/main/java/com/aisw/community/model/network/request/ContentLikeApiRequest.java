package com.aisw.community.model.network.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentLikeApiRequest {

    private Long accountId;

    private Long boardId;

    private Long commentId;
}
