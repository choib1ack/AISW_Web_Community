package com.aisw.community.model.network.response;

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

    private String category;

    private String title;

    private String createdBy;

    private LocalDateTime createdAt;

    private Long views;
}
