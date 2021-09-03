package com.aisw.community.repository.admin;

import com.aisw.community.model.network.response.admin.SiteInformationByCategoryResponse;

import java.util.List;

public interface CustomSiteInformationRepository {

    List<SiteInformationByCategoryResponse> findAllByCategory();
}
