package com.aisw.community.model.network.request;

import com.aisw.community.model.enumclass.BulletinStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaApiRequest {

    private Long id;

    private String title;

    private String content;

    private String attachmentFile;

    private BulletinStatus status;

    private String subject;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    private Long accountId;
}
