package com.aisw.community.model.network.request.post.notice;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.Campus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UniversityApiRequest {

    private Long id;

    private String title;

    private String content;

    // 긴급0, 상단고정1, 일반2
    private BulletinStatus status;

    private Campus campus;
}
