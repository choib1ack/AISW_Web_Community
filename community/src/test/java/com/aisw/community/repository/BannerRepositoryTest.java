package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.admin.Banner;
import com.aisw.community.model.entity.post.notice.University;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.Campus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.repository.admin.BannerRepository;
import com.aisw.community.repository.admin.CustomBannerRepository;
import com.aisw.community.repository.post.notice.UniversityRepository;
import com.aisw.community.repository.user.UserRepository;
import com.aisw.community.service.post.notice.UniversityService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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