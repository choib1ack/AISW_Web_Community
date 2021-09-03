package com.aisw.community.service.admin;

import com.aisw.community.component.advice.exception.SiteCategoryNameNotFoundException;
import com.aisw.community.component.advice.exception.SiteInformationNotFoundException;
import com.aisw.community.model.entity.admin.SiteCategory;
import com.aisw.community.model.entity.admin.SiteInformation;
import com.aisw.community.model.entity.post.file.File;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.SiteInformationApiRequest;
import com.aisw.community.model.network.response.admin.SiteInformationApiResponse;
import com.aisw.community.model.network.response.admin.SiteInformationWithFileApiResponse;
import com.aisw.community.model.network.response.admin.SiteInformationByCategoryResponse;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.repository.admin.CustomSiteInformationRepository;
import com.aisw.community.repository.admin.SiteCategoryRepository;
import com.aisw.community.repository.admin.SiteInformationRepository;
import com.aisw.community.service.post.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteInformationService {

    @Autowired
    private SiteInformationRepository siteInformationRepository;

    @Autowired
    private CustomSiteInformationRepository customSiteInformationRepository;

    @Autowired
    private SiteCategoryRepository siteCategoryRepository;

    @Autowired
    private FileService fileService;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "readSite", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<SiteInformationApiResponse> create(User user, SiteInformationApiRequest siteInformationApiRequest, MultipartFile[] files) {
        String url = siteInformationApiRequest.getLinkUrl();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            siteInformationApiRequest.setLinkUrl("http://" + url);
        }

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

        if (files != null) {
            List<FileApiResponse> fileApiResponseList =
                    fileService.uploadFiles(files, user.getUsername(), null, newSiteInformation.getId(), UploadCategory.SITE);

            return Header.OK(response(newSiteInformation, fileApiResponseList));
        } else {
            return Header.OK(response(newSiteInformation));
        }
    }

    @Cacheable(value = "readSite")
    public Header<List<SiteInformationWithFileApiResponse>> readAll() {
        List<SiteInformationByCategoryResponse> siteInformationByCategoryResponseList = customSiteInformationRepository.findAllGroupByCategory();
        siteInformationByCategoryResponseList.stream().forEach(siteInformation -> System.out.println(siteInformation.getName() + " " + siteInformation.getSiteInformation()));

        List<SiteInformationWithFileApiResponse> siteInformationWithFileApiResponseList = new ArrayList<>();
        SiteInformationWithFileApiResponse siteInformationWithFileApiResponse = null;
        String prev = "";
        for(int i = 0; i < siteInformationByCategoryResponseList.size(); i++) {
            SiteInformationByCategoryResponse now = siteInformationByCategoryResponseList.get(i);
            if(prev.equals("") || !prev.equals(now.getName())) {
                if(siteInformationWithFileApiResponse != null) {
                    siteInformationWithFileApiResponseList.add(siteInformationWithFileApiResponse);
                }
                siteInformationWithFileApiResponse = new SiteInformationWithFileApiResponse(now.getId(), now.getName());
                prev = now.getName();
            }
            if(now.getSiteInformation() != null) {
                siteInformationWithFileApiResponse.getSiteInformationApiResponseList().add(response(now.getSiteInformation()));
            }
        }
        if(siteInformationWithFileApiResponse != null) {
            siteInformationWithFileApiResponseList.add(siteInformationWithFileApiResponse);
        }

        return Header.OK(siteInformationWithFileApiResponseList);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "readSite", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<SiteInformationApiResponse> update(User user, SiteInformationApiRequest siteInformationApiRequest, MultipartFile[] files, List<Long> delFileIdList) {
        SiteInformation siteInformation = siteInformationRepository.findById(siteInformationApiRequest.getId())
                .orElseThrow(() -> new SiteInformationNotFoundException(siteInformationApiRequest.getId()));
        SiteCategory siteCategory = siteCategoryRepository.findByName(siteInformationApiRequest.getCategory())
                .orElseThrow(() -> new SiteCategoryNameNotFoundException(siteInformationApiRequest.getCategory()));

        String url = siteInformationApiRequest.getLinkUrl();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            siteInformationApiRequest.setLinkUrl("http://" + url);
        }

        siteInformation
                .setName(siteInformationApiRequest.getName())
                .setContent(siteInformationApiRequest.getContent())
                .setLinkUrl(siteInformationApiRequest.getLinkUrl())
                .setPublishStatus(siteInformationApiRequest.getPublishStatus())
                .setSiteCategory(siteCategory);
        siteInformationRepository.save(siteInformation);

        if(siteInformation.getFileList() != null && delFileIdList != null) {
            List<File> delFileList = new ArrayList<>();
            for(File file : siteInformation.getFileList()) {
                if(delFileIdList.contains(file.getId())) {
                    fileService.deleteFile(file);
                    delFileList.add(file);
                }
            }
            for (File file : delFileList) {
                siteInformation.getFileList().remove(file);
            }
        }
        if (files != null) {
            List<FileApiResponse> fileApiResponseList =
                    fileService.uploadFiles(files, user.getUsername(), null, siteInformation.getId(), UploadCategory.SITE);
            return Header.OK(response(siteInformation, fileApiResponseList));
        } else {
            return Header.OK(response(siteInformation));
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "readSite", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header delete(Long id) {
        SiteInformation siteInformation = siteInformationRepository.findById(id)
                .orElseThrow(() -> new SiteInformationNotFoundException(id));
        fileService.deleteFileList(siteInformation.getFileList());
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
                .fileApiResponseList(fileService.getFileList(siteInformation.getFileList()))
                .build();

        return siteInformationApiResponse;
    }

    private SiteInformationApiResponse response(SiteInformation siteInformation, List<FileApiResponse> fileApiResponseList) {
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
                .fileApiResponseList(fileApiResponseList)
                .build();

        return siteInformationApiResponse;
    }
}
