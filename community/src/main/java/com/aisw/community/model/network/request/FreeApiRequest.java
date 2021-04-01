package com.aisw.community.model.network.request;

import com.aisw.community.model.enumclass.BulletinStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreeApiRequest {

    private Long id;

    private String title;

    private String content;

    private String attachmentFile;

    private BulletinStatus status;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    private Long accountId;
}
