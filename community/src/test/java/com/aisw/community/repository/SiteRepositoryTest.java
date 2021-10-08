package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.admin.Banner;
import com.aisw.community.model.network.response.admin.SiteInformationByCategoryResponse;
import com.aisw.community.repository.admin.BannerRepository;
import com.aisw.community.repository.admin.CustomSiteInformationRepository;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class SiteRepositoryTest extends CommunityApplicationTests {

    @Autowired
    private CustomSiteInformationRepository customSiteInformationRepository;

    @Test
    public void findAllByCategory() {
        List<SiteInformationByCategoryResponse> allByCategory = customSiteInformationRepository.findAllByCategory();
    }
}