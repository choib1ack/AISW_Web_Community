package com.aisw.community.service;

import com.aisw.community.model.entity.admin.Banner;
import com.aisw.community.model.entity.admin.SiteInformation;
import com.aisw.community.model.entity.post.Bulletin;
import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.entity.post.notice.Notice;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.HomeApiResponse;
import com.aisw.community.model.network.response.admin.HomeBannerAndSiteResponse;
import com.aisw.community.model.network.response.post.board.BoardApiResponse;
import com.aisw.community.repository.admin.BannerRepository;
import com.aisw.community.repository.admin.SiteInformationRepository;
import com.aisw.community.repository.post.board.BoardRepository;
import com.aisw.community.repository.post.board.FreeRepository;
import com.aisw.community.repository.post.board.QnaRepository;
import com.aisw.community.repository.post.notice.CouncilRepository;
import com.aisw.community.repository.post.notice.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeApiService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private SiteInformationRepository siteInformationRepository;

    @Cacheable(value = "home")
    public Header<HomeApiResponse> main() {
        List<Notice> noticeList = noticeRepository.findTop10ByOrderByCreatedAtDesc();
        List<Board> boardList = boardRepository.findTop10ByOrderByCreatedAtDesc();
        HomeApiResponse homeApiResponse = HomeApiResponse.builder()
                .noticeList(noticeList.stream().map(notice -> response(notice)).collect(Collectors.toList()))
                .boardList(boardList.stream().map(board -> response(board)).collect(Collectors.toList()))
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
