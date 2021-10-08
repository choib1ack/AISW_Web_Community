package com.aisw.community.model.network.response.admin;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class SiteInformationWithFileApiResponse {

    private Long id;

    private String name;

    private List<SiteInformationApiResponse> siteInformationApiResponseList;

    public SiteInformationWithFileApiResponse() {
        siteInformationApiResponseList = new ArrayList<>();
    }
}
