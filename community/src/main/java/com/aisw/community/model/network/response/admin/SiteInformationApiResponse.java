package com.aisw.community.model.network.response.admin;


import com.aisw.community.model.enumclass.InformationCategory;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

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

    private InformationCategory informationCategory;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;
}
