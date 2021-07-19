package com.aisw.community.service.admin;

import com.aisw.community.advice.exception.SiteCategoryNotFoundException;
import com.aisw.community.model.entity.admin.SiteCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.admin.SiteCategoryApiResponse;
import com.aisw.community.repository.admin.SiteCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteCategoryApiLogicService {

    @Autowired
    private SiteCategoryRepository siteCategoryRepository;

    @Autowired
    private SiteInformationApiLogicService siteInformationApiLogicService;

    public Header<SiteCategoryApiResponse> create(String name) {
        SiteCategory siteCategory = SiteCategory.builder().name(name).build();
        SiteCategory newSiteCategory = siteCategoryRepository.save(siteCategory);

        return Header.OK(response(newSiteCategory));
    }

    public Header<List<SiteCategoryApiResponse>> readAll() {
        List<SiteCategory> siteCategoryList = siteCategoryRepository.findAll();
        List<SiteCategoryApiResponse> siteCategoryApiResponseList = siteCategoryList.stream()
                .map(this::responseWithInfo).collect(Collectors.toList());
        return Header.OK(siteCategoryApiResponseList);
    }

    public Header<SiteCategoryApiResponse> update(Long id, String name) {
        SiteCategory siteCategory = siteCategoryRepository.findById(id)
                .orElseThrow(() -> new SiteCategoryNotFoundException(id));

        siteCategory.setName(name);
        siteCategoryRepository.save(siteCategory);

        return Header.OK(response(siteCategory));
    }

    public Header delete(Long id) {
        SiteCategory siteCategory = siteCategoryRepository.findById(id)
                .orElseThrow(() -> new SiteCategoryNotFoundException(id));

        siteCategoryRepository.delete(siteCategory);
        return Header.OK();
    }

    private SiteCategoryApiResponse response(SiteCategory category) {
        SiteCategoryApiResponse siteCategoryApiResponse = SiteCategoryApiResponse.builder()
                .id(category.getId()).name(category.getName()).build();
        return siteCategoryApiResponse;
    }

    private SiteCategoryApiResponse responseWithInfo(SiteCategory category) {
        return SiteCategoryApiResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .siteInformationApiResponseList(category.getSiteInformationList().stream()
                        .map(siteInformation -> siteInformationApiLogicService.response(siteInformation))
                        .collect(Collectors.toList()))
                .build();
    }
}