package com.aisw.community.model.network.response.admin;


import com.aisw.community.model.enumclass.InformationCategory;
import lombok.*;

import java.time.LocalDateTime;

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
}
