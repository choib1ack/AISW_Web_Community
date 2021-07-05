package com.aisw.community.service.admin;

import com.aisw.community.advice.exception.BannerNotFoundException;
import com.aisw.community.model.entity.admin.Banner;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.admin.BannerApiRequest;
import com.aisw.community.model.network.response.admin.BannerApiResponse;
import com.aisw.community.repository.admin.BannerRepository;
import com.aisw.community.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BannerApiLogicService extends BaseService<BannerApiRequest, BannerApiResponse, Banner> {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public Header<BannerApiResponse> create(Header<BannerApiRequest> request) {
        BannerApiRequest bannerApiRequest = request.getData();

        Banner banner = Banner.builder()
                .name(bannerApiRequest.getName())
                .content(bannerApiRequest.getContent())
                .startDate(LocalDateTime.parse(bannerApiRequest.getStartDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .endDate(LocalDateTime.parse(bannerApiRequest.getEndDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .linkUrl(bannerApiRequest.getLinkUrl())
                .publishStatus(bannerApiRequest.getPublishStatus())
                .build();

        Banner newBanner = baseRepository.save(banner);

        return Header.OK(response(newBanner));
    }

    @Override
    public Header<BannerApiResponse> read(Long id) {
        return null;
    }

    @Override
    public Header<BannerApiResponse> update(Header<BannerApiRequest> request) {
        BannerApiRequest bannerApiRequest = request.getData();

        Banner banner = baseRepository.findById(bannerApiRequest.getId()).orElseThrow(
                () -> new BannerNotFoundException(bannerApiRequest.getId()));

        banner.setName(bannerApiRequest.getName())
                .setContent(bannerApiRequest.getContent())
                .setStartDate(LocalDateTime.parse(bannerApiRequest.getStartDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .setEndDate(LocalDateTime.parse(bannerApiRequest.getEndDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .setLinkUrl(bannerApiRequest.getLinkUrl())
                .setPublishStatus(bannerApiRequest.getPublishStatus());
        baseRepository.save(banner);

        return Header.OK(response(banner));
    }

    @Override
    public Header delete(Long id, Long userId) {
        return null;
    }

    public Header delete(Long id) {
        Banner banner = baseRepository.findById(id).orElseThrow(() -> new BannerNotFoundException(id));
        baseRepository.delete(banner);
        return Header.OK();
    }

    public Header<List<BannerApiResponse>> readBanner(Pageable pageable) {
        Page<Banner> bannerList = bannerRepository.findAllByPublishStatus(Boolean.TRUE, pageable);

        List<BannerApiResponse> bannerApiResponseList = bannerList.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(bannerList.getTotalElements())
                .totalPages(bannerList.getTotalPages())
                .currentElements(bannerList.getNumberOfElements())
                .currentPage(bannerList.getNumber())
                .build();

        return Header.OK(bannerApiResponseList, pagination);
    }

    private BannerApiResponse response(Banner banner){
        BannerApiResponse bannerApiResponse = BannerApiResponse.builder()
                .id(banner.getId())
                .name(banner.getName())
                .content(banner.getContent())
                .startDate(banner.getStartDate())
                .endDate(banner.getEndDate())
                .linkUrl(banner.getLinkUrl())
                .publishStatus(banner.getPublishStatus())
                .createdAt(banner.getCreatedAt())
                .createdBy(banner.getCreatedBy())
                .updatedAt(banner.getUpdatedAt())
                .updatedBy(banner.getUpdatedBy())
                .fileSet(banner.getFile())
                .build();

        return bannerApiResponse;
    }

    @Scheduled(cron = "0 0 4 * * *")
    private void checkEndDate() {
        LocalDateTime now = LocalDateTime.now();

        List<Banner> bannerList = bannerRepository.findAllByPublishStatus(Boolean.TRUE);

        bannerList.stream().forEach(banner -> {
            if(now.isBefore(banner.getStartDate())) {
                banner.setPublishStatus(Boolean.FALSE);
                baseRepository.save(banner);
            }
        });
    }

    @Override
    public Header<List<BannerApiResponse>> search(Pageable pageable) {
        return null;
    }
}
