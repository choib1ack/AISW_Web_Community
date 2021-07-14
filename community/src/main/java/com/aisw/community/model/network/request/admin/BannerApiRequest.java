package com.aisw.community.model.network.request.admin;


import lombok.*;

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
}