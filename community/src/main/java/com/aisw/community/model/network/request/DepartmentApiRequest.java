package com.aisw.community.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentApiRequest {

    private Long id;

    private String title;

    private String content;

    private String attachmentFile;

    // 긴급0, 상단고정1, 일반2
    private Long status;

    private Long views;

    // 학교 공지 0
    private Long level;

    private Long noticeId;
}
