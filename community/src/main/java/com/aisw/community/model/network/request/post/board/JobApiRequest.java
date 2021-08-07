package com.aisw.community.model.network.request.post.board;

import com.aisw.community.model.enumclass.BulletinStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobApiRequest {

    private Long id;

    private String title;

    private String content;

    private BulletinStatus status;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;
}
