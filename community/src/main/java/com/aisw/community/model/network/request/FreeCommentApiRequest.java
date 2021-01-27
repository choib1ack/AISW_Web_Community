package com.aisw.community.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreeCommentApiRequest {

    private Long id;

    private String content;

    private Long isAnonymous;

    private Long likes;

    private Long freeId;
}
