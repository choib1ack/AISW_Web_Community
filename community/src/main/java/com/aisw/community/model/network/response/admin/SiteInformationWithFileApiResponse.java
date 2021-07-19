package com.aisw.community.model.network.response.admin;


import com.aisw.community.model.network.response.post.file.FileApiResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteInformationWithFileApiResponse {

    private Long id;

    private String name;

    private List<SiteInformationApiResponse> siteInformationApiResponseList;

    public SiteInformationWithFileApiResponse(Long id, String name) {
        this.id = id;
        this.name = name;
        siteInformationApiResponseList = new ArrayList<>();
    }
}
