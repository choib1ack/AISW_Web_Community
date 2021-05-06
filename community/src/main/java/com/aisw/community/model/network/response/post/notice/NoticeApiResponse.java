package com.aisw.community.model.network.response.post.notice;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeApiResponse {

    private Long id;

    private SecondCategory category;

    private String title;

    private BulletinStatus status;

    private String writer;

    private LocalDateTime createdAt;

    private Long views;
}
