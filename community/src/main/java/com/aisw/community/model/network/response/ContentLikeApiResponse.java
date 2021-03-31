package com.aisw.community.model.network.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ContentLikeApiResponse {

    private Long id;

    private Long accountId;

    private Long boardId;

    private Long commentId;
}
