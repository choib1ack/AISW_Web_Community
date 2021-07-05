package com.aisw.community.model.network.response.admin;

import com.aisw.community.model.enumclass.InformationCategory;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SiteInformationApiResponseDTO {

     private InformationCategory category;

     private List<SiteInformationApiResponse> siteInformationApiResponseList = new ArrayList<>();
}
