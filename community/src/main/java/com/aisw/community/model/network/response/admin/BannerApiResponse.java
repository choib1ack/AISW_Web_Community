package com.aisw.community.model.network.response.admin;


import com.aisw.community.model.entity.post.file.File;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerApiResponse {

    private Long id;

    private String name;

    private String content;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean publishStatus;

    private String linkUrl;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private List<FileApiResponse> fileApiResponseList;
}
