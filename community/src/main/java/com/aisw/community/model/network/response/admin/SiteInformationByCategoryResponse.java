package com.aisw.community.model.network.response.admin;


import com.aisw.community.model.entity.admin.SiteInformation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SiteInformationByCategoryResponse {

    private Long categoryId;

    private String categoryName;

    private SiteInformation siteInformation;
}
