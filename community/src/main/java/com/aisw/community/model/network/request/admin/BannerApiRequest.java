package com.aisw.community.model.network.request.admin;


import com.aisw.community.model.enumclass.BannerCategory;
import com.aisw.community.model.enumclass.BannerSubCategory;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerApiRequest {

    private Long id;

    private String name;

    private String content;

    private String startDate;

    private String endDate;

    private Boolean publishStatus;

    private String linkUrl;

    private BannerCategory category;

    private BannerSubCategory subCategory;
}