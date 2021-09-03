package com.aisw.community.service.admin;

import com.aisw.community.component.advice.exception.SiteCategoryNotFoundException;
import com.aisw.community.model.entity.admin.SiteCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.admin.SiteCategoryApiResponse;
import com.aisw.community.repository.admin.SiteCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class SiteCategoryService {

    @Autowired
    private SiteCategoryRepository siteCategoryRepository;

    @Caching(evict = {
            @CacheEvict(value = "siteRead", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<SiteCategoryApiResponse> create(String name) {
        SiteCategory siteCategory = SiteCategory.builder().name(name).build();
        SiteCategory newSiteCategory = siteCategoryRepository.save(siteCategory);

        return Header.OK(response(newSiteCategory));
    }

    @Caching(evict = {
            @CacheEvict(value = "siteRead", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<SiteCategoryApiResponse> update(Long id, String name) {
        SiteCategory siteCategory = siteCategoryRepository.findById(id)
                .orElseThrow(() -> new SiteCategoryNotFoundException(id));

        siteCategory.setName(name);
        siteCategoryRepository.save(siteCategory);

        return Header.OK(response(siteCategory));
    }

    @Caching(evict = {
            @CacheEvict(value = "siteRead", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
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
}