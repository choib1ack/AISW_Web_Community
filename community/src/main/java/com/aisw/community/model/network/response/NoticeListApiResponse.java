package com.aisw.community.model.network.response;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.NoticeCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeListApiResponse {

    private Long id;

    private NoticeCategory category;

    private String title;

    private BulletinStatus status;

    private String writer;

    private LocalDateTime createdAt;

    private Long views;
}
