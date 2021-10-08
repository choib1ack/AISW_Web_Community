package com.aisw.community.service;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.admin.SiteInformationByCategoryResponse;
import com.aisw.community.model.network.response.admin.SiteInformationWithFileApiResponse;
import com.aisw.community.repository.admin.CustomSiteInformationRepository;
import com.aisw.community.service.admin.SiteInformationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SiteServiceTest extends CommunityApplicationTests {

    @Autowired
    private SiteInformationService siteInformationService;

    @Test
    public void readAll() {
        Header<List<SiteInformationWithFileApiResponse>> readAll = siteInformationService.readAll();
    }
}