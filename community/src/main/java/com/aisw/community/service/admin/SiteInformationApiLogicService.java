package com.aisw.community.service.admin;

import com.aisw.community.advice.exception.SiteCategoryNameNotFoundException;
import com.aisw.community.advice.exception.SiteInformationNotFoundException;
import com.aisw.community.model.entity.admin.SiteCategory;
import com.aisw.community.model.entity.admin.SiteInformation;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.FileUploadToSiteInformationDTO;
import com.aisw.community.model.network.request.admin.SiteInformationApiRequest;
import com.aisw.community.model.network.response.admin.SiteInformationApiResponse;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.repository.admin.SiteCategoryRepository;
import com.aisw.community.repository.admin.SiteInformationRepository;
import com.aisw.community.repository.post.file.FileRepository;
import com.aisw.community.service.post.file.FileApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteInformationApiLogicService {

    @Autowired
    private SiteInformationRepository siteInformationRepository;

    @Autowired
    private SiteCategoryRepository siteCategoryRepository;

    @Autowired
    private FileRepository fileRepository;


    @Autowired
    private FileApiLogicService fileApiLogicService;

    @Transactional
    public Header<SiteInformationApiResponse> create(FileUploadToSiteInformationDTO request) {
        SiteInformationApiRequest siteInformationApiRequest = request.getSiteInformationApiRequest();

        SiteCategory siteCategory = siteCategoryRepository.findByName(siteInformationApiRequest.getCategory())
                .orElseThrow(() -> new SiteCategoryNameNotFoundException(siteInformationApiRequest.getCategory()));

        SiteInformation siteInformation = SiteInformation.builder()
                .name(siteInformationApiRequest.getName())
                .content(siteInformationApiRequest.getContent())
                .linkUrl(siteInformationApiRequest.getLinkUrl())
                .publishStatus(siteInformationApiRequest.getPublishStatus())
                .siteCategory(siteCategory)
                .build();

        SiteInformation newSiteInformation = siteInformationRepository.save(siteInformation);

        MultipartFile[] files = request.getFiles();
        List<FileApiResponse> fileApiResponseList = fileApiLogicService.uploadFiles(files, newSiteInformation.getId(), UploadCategory.SITE);

        return Header.OK(response(newSiteInformation, fileApiResponseList));
    }

    @Transactional
    public Header<SiteInformationApiResponse> update(FileUploadToSiteInformationDTO request) {
        SiteInformationApiRequest siteInformationApiRequest = request.getSiteInformationApiRequest();
        MultipartFile[] files = request.getFiles();

        SiteInformation siteInformation = siteInformationRepository.findById(siteInformationApiRequest.getId())
                .orElseThrow(() -> new SiteInformationNotFoundException(siteInformationApiRequest.getId()));
        SiteCategory siteCategory = siteCategoryRepository.findByName(siteInformationApiRequest.getCategory())
                .orElseThrow(() -> new SiteCategoryNameNotFoundException(siteInformationApiRequest.getCategory()));

        siteInformation.getFileList().stream().forEach(file -> fileRepository.delete(file));
        List<FileApiResponse> fileApiResponseList = fileApiLogicService.uploadFiles(files, siteInformation.getId(), UploadCategory.SITE);
        siteInformation.setName(siteInformationApiRequest.getName())
                .setContent(siteInformationApiRequest.getContent())
                .setLinkUrl(siteInformationApiRequest.getLinkUrl())
                .setPublishStatus(siteInformationApiRequest.getPublishStatus())
                .setSiteCategory(siteCategory);
        siteInformationRepository.save(siteInformation);

        return Header.OK(response(siteInformation, fileApiResponseList));
    }

    public Header delete(Long id) {
        SiteInformation siteInformation = siteInformationRepository.findById(id).orElseThrow(() -> new SiteInformationNotFoundException(id));
        siteInformationRepository.delete(siteInformation);
        return Header.OK();
    }

    public SiteInformationApiResponse response(SiteInformation siteInformation) {
        SiteInformationApiResponse siteInformationApiResponse = SiteInformationApiResponse.builder()
                .id(siteInformation.getId())
                .name(siteInformation.getName())
                .content(siteInformation.getContent())
                .linkUrl(siteInformation.getLinkUrl())
                .publishStatus(siteInformation.getPublishStatus())
                .category(siteInformation.getSiteCategory().getName())
                .createdAt(siteInformation.getCreatedAt())
                .createdBy(siteInformation.getCreatedBy())
                .updatedAt(siteInformation.getUpdatedAt())
                .updatedBy(siteInformation.getUpdatedBy())
                .fileApiResponseList(siteInformation.getFileList().stream()
                        .map(file -> fileApiLogicService.response(file)).collect(Collectors.toList()))
                .build();

        return siteInformationApiResponse;
    }

    private SiteInformationApiResponse response(SiteInformation siteInformation, List<FileApiResponse> fileList) {
        SiteInformationApiResponse siteInformationApiResponse = SiteInformationApiResponse.builder()
                .id(siteInformation.getId())
                .name(siteInformation.getName())
                .content(siteInformation.getContent())
                .linkUrl(siteInformation.getLinkUrl())
                .publishStatus(siteInformation.getPublishStatus())
                .category(siteInformation.getSiteCategory().getName())
                .createdAt(siteInformation.getCreatedAt())
                .createdBy(siteInformation.getCreatedBy())
                .updatedAt(siteInformation.getUpdatedAt())
                .updatedBy(siteInformation.getUpdatedBy())
                .fileApiResponseList(fileList)
                .build();

        return siteInformationApiResponse;
    }
}
