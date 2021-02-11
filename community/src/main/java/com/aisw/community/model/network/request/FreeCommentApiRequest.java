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

    private String comment;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    private Long likes;

    private Long freeId;
}
