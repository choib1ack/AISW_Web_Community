package com.aisw.community.model.network.request.post.notice;

import com.aisw.community.model.enumclass.BulletinStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentApiRequest {

    private Long id;

    private String title;

    private String content;

    // 긴급0, 상단고정1, 일반2
    private BulletinStatus status;

    private Long userId;
}
