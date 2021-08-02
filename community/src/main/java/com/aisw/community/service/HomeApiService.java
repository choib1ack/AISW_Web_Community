package com.aisw.community.service;

import com.aisw.community.model.entity.admin.Banner;
import com.aisw.community.model.entity.admin.SiteInformation;
import com.aisw.community.model.entity.post.Bulletin;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.HomeApiResponse;
import com.aisw.community.model.network.response.admin.HomeBannerAndSiteResponse;
import com.aisw.community.model.network.response.post.board.BoardApiResponse;
import com.aisw.community.repository.admin.BannerRepository;
import com.aisw.community.repository.admin.SiteInformationRepository;
import com.aisw.community.repository.post.board.FreeRepository;
import com.aisw.community.repository.post.board.QnaRepository;
import com.aisw.community.repository.post.notice.CouncilRepository;
import com.aisw.community.repository.post.notice.DepartmentRepository;
import com.aisw.community.repository.post.notice.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class HomeApiService {

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CouncilRepository councilRepository;

    @Autowired
    private FreeRepository freeRepository;

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private SiteInformationRepository siteInformationRepository;


    public Header<HomeApiResponse> main() {
        HomeApiResponse homeApiResponse = HomeApiResponse.builder()
                .universityList(universityRepository.findTop10ByOrderByCreatedAtDesc()
                        .stream().map(university -> response(university)).collect(Collectors.toList()))
                .departmentList(departmentRepository.findTop10ByOrderByCreatedAtDesc()
                        .stream().map(department -> response(department)).collect(Collectors.toList()))
                .councilList(councilRepository.findTop10ByOrderByCreatedAtDesc()
                        .stream().map(council -> response(council)).collect(Collectors.toList()))
                .freeList(freeRepository.findTop10ByOrderByCreatedAtDesc()
                        .stream().map(free -> response(free)).collect(Collectors.toList()))
                .qnaList(qnaRepository.findTop10ByOrderByCreatedAtDesc()
                        .stream().map(qna -> response(qna)).collect(Collectors.toList()))
                .bannerList(bannerRepository.findAllByPublishStatus(true)
                        .stream().map(banner -> response(banner)).collect(Collectors.toList()))
                .siteList(siteInformationRepository.findAllFetchJoinWithFile()
                        .stream().map(siteInformation -> response(siteInformation)).collect(Collectors.toList()))
                .build();

        return Header.OK(homeApiResponse);
    }

    private BoardApiResponse response(Bulletin bulletin) {
        return BoardApiResponse.builder()
                .id(bulletin.getId())
                .title(bulletin.getTitle())
                .status(bulletin.getStatus())
                .category(bulletin.getSecondCategory())
                .writer(bulletin.getWriter())
                .createdAt(bulletin.getCreatedAt())
                .views(bulletin.getViews())
                .build();
    }

    private HomeBannerAndSiteResponse response(Banner banner) {
        return HomeBannerAndSiteResponse.builder()
                .linkUrl(banner.getLinkUrl())
                .fileDownloadUri(banner.getFileList().stream()
                        .map(file -> file.getFileDownloadUri()).collect(Collectors.toList()))
                .build();
    }

    private HomeBannerAndSiteResponse response(SiteInformation site) {
        return HomeBannerAndSiteResponse.builder()
                .linkUrl(site.getLinkUrl())
                .fileDownloadUri(site.getFileList().stream()
                        .map(file -> file.getFileDownloadUri()).collect(Collectors.toList()))
                .build();
    }
}
