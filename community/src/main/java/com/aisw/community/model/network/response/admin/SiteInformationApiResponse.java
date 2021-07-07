package com.aisw.community.model.network.response.admin;


import com.aisw.community.model.entity.post.file.File;
import com.aisw.community.model.enumclass.InformationCategory;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

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
public class SiteInformationApiResponse {

    private Long id;

    private String name;

    private String content;

    private Boolean publishStatus;

    private String linkUrl;

    private InformationCategory category;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private List<FileApiResponse> fileApiResponseList;
}
