package com.aisw.community.model.network.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeBannerAndSiteResponse {

    private String linkUrl;

    private List<String> fileDownloadUri;
}
