package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.admin.Banner;
import com.aisw.community.repository.admin.BannerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class BannerRepositoryTest extends CommunityApplicationTests {

    @Autowired
    private CustomBannerRepository customBannerRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Test
    public void findAllFetchJoinWithFile() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("endDate").descending());
        Page<Banner> allFetchJoinWithFile = bannerRepository.findAll(pageRequest);
        allFetchJoinWithFile.stream().forEach(System.out::println);
    }
}