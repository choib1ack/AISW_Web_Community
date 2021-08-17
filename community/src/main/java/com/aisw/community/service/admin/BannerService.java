package com.aisw.community.service.admin;

import com.aisw.community.component.advice.exception.BannerNotFoundException;
import com.aisw.community.model.entity.admin.Banner;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.admin.BannerApiRequest;
import com.aisw.community.model.network.request.admin.FileUploadToBannerRequest;
import com.aisw.community.model.network.response.admin.BannerApiResponse;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.repository.admin.BannerRepository;
import com.aisw.community.repository.admin.CustomBannerRepository;
import com.aisw.community.repository.post.file.FileRepository;
import com.aisw.community.service.post.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private CustomBannerRepository customBannerRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "bannerRead", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<BannerApiResponse> create(FileUploadToBannerRequest request) {
        BannerApiRequest bannerApiRequest = request.getBannerApiRequest();

        Banner banner = Banner.builder()
                .name(bannerApiRequest.getName())
                .content(bannerApiRequest.getContent())
                .startDate(LocalDate.parse(bannerApiRequest.getStartDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .endDate(LocalDate.parse(bannerApiRequest.getEndDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .linkUrl(bannerApiRequest.getLinkUrl())
                .build();

        Banner newBanner = bannerRepository.save(banner);

        MultipartFile[] files = request.getFiles();
        List<FileApiResponse> fileApiResponseList = fileService.uploadFiles(files, newBanner.getId(), UploadCategory.BANNER);

        return Header.OK(response(newBanner, fileApiResponseList));
    }

    @Cacheable(value = "bannerRead", key = "#pageable.pageNumber")
    public Header<List<BannerApiResponse>> readAll(Pageable pageable) {
        Page<Banner> bannerList = customBannerRepository.findAllFetchJoinWithFile(pageable);

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

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "bannerRead", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<BannerApiResponse> update(FileUploadToBannerRequest request) {
        BannerApiRequest bannerApiRequest = request.getBannerApiRequest();
        MultipartFile[] files = request.getFiles();

        Banner banner = bannerRepository.findById(bannerApiRequest.getId()).orElseThrow(
                () -> new BannerNotFoundException(bannerApiRequest.getId()));

        banner.getFileList().stream().forEach(file -> fileRepository.delete(file));
        banner.getFileList().clear();
        List<FileApiResponse> fileApiResponseList = fileService.uploadFiles(files, banner.getId(), UploadCategory.BANNER);

        banner.setName(bannerApiRequest.getName())
                .setContent(bannerApiRequest.getContent())
                .setStartDate(LocalDate.parse(bannerApiRequest.getStartDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setEndDate(LocalDate.parse(bannerApiRequest.getEndDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setLinkUrl(bannerApiRequest.getLinkUrl());
        Banner updateBanner = bannerRepository.save(banner);

        return Header.OK(response(updateBanner, fileApiResponseList));
    }

    @Caching(evict = {
            @CacheEvict(value = "bannerRead", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header delete(Long id) {
        Banner banner = bannerRepository.findById(id).orElseThrow(() -> new BannerNotFoundException(id));
        fileService.delete(banner.getFileList());
        bannerRepository.delete(banner);
        return Header.OK();
    }

    @Scheduled(cron = "0 0 4 * * *")
    @Caching(evict = {
            @CacheEvict(value = "bannerRead", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public void checkEndDate() {
        LocalDate now = LocalDate.now();

        List<Banner> bannerList = bannerRepository.findAllByPublishStatus(Boolean.TRUE);

        bannerList.stream().forEach(banner -> {
            if (now.isEqual(banner.getStartDate()) || now.isAfter(banner.getStartDate())
                    && (now.isEqual(banner.getEndDate()) || now.isBefore(banner.getEndDate()))) {
                banner.setPublishStatus(Boolean.TRUE);
            } else {
                banner.setPublishStatus(Boolean.FALSE);
            }
            bannerRepository.save(banner);
        });
    }

    private BannerApiResponse response(Banner banner) {
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
                .fileApiResponseList(banner.getFileList().stream()
                        .map(file -> fileService.response(file)).collect(Collectors.toList()))
                .build();

        return bannerApiResponse;
    }

    private BannerApiResponse response(Banner banner, List<FileApiResponse> fileApiResponseList) {
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
                .fileApiResponseList(fileApiResponseList)
                .build();

        return bannerApiResponse;
    }
}
