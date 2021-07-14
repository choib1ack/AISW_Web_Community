package com.aisw.community.model.network.response.admin;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteCategoryApiResponse {

    private Long id;

    private String name;

    private List<SiteInformationApiResponse> siteInformationApiResponseList;
}
