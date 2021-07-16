package com.aisw.community.model.network.response.post.notice;

import com.aisw.community.model.entity.post.file.File;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouncilApiResponse {

    private Long id;

    private String title;

    private String writer;

    private String content;

    // 긴급0, 상단고정1, 일반2
    private BulletinStatus status;

    private Long views;

    private SecondCategory category;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private Long accountId;

    private List<FileApiResponse> fileApiResponseList;
}
