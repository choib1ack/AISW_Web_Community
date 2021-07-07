package com.aisw.community.service.admin;

import com.aisw.community.advice.exception.SiteInformationNotFoundException;
import com.aisw.community.model.entity.admin.SiteInformation;
import com.aisw.community.model.entity.post.file.File;
import com.aisw.community.model.enumclass.InformationCategory;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.SiteInformationApiRequest;
import com.aisw.community.model.network.request.admin.SiteInformationApiRequestDTO;
import com.aisw.community.model.network.response.admin.SiteInformationApiResponse;
import com.aisw.community.model.network.response.admin.SiteInformationApiResponseDTO;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.repository.admin.SiteInformationRepository;
import com.aisw.community.repository.post.file.FileRepository;
import com.aisw.community.service.post.file.FileApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteInformationApiLogicService {

    @Autowired
    private SiteInformationRepository siteInformationRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileApiLogicService fileApiLogicService;

    @Transactional
    public Header<SiteInformationApiResponse> create(SiteInformationApiRequestDTO request) {
        SiteInformationApiRequest siteInformationApiRequest = request.getSiteInformationApiRequest();

        SiteInformation siteInformation = SiteInformation.builder()
                .name(siteInformationApiRequest.getName())
                .content(siteInformationApiRequest.getContent())
                .linkUrl(siteInformationApiRequest.getLinkUrl())
                .publishStatus(siteInformationApiRequest.getPublishStatus())
                .category(siteInformationApiRequest.getCategory())
                .build();

        SiteInformation newSiteInformation = siteInformationRepository.save(siteInformation);

        MultipartFile[] files = request.getFiles();
        List<FileApiResponse> fileApiResponseList = fileApiLogicService.uploadFiles(files, newSiteInformation.getId(), UploadCategory.SITE);

        return Header.OK(response(newSiteInformation, fileApiResponseList));
    }

    public Header<List<SiteInformationApiResponseDTO>> readAll() {
        List<SiteInformation> siteInformationList = siteInformationRepository.findAllByPublishStatus(Boolean.TRUE);
        Collections.sort(siteInformationList, Comparator.comparing(siteInformation -> siteInformation.getCategory().getId()));

        List<InformationCategory> categoryList = new ArrayList<>();
        categoryList.add(InformationCategory.CODINGTEST);
        categoryList.add(InformationCategory.LECTURE);
        categoryList.add(InformationCategory.RECRUITMENT);
        categoryList.add(InformationCategory.ACTIVITY);

        List<SiteInformationApiResponseDTO> siteInformationApiResponseDTOList = new ArrayList<>();
        SiteInformationApiResponseDTO siteInformationApiResponseDTO = null;
        String category = new String();
        for(SiteInformation siteInformation : siteInformationList) {
            if(!category.equals(siteInformation.getCategory().getTitle())) {
                if(category.length() != 0) {
                    siteInformationApiResponseDTOList.add(siteInformationApiResponseDTO);
                }
                siteInformationApiResponseDTO = new SiteInformationApiResponseDTO();
                siteInformationApiResponseDTO.setCategory(siteInformation.getCategory());
                siteInformationApiResponseDTO.getSiteInformationApiResponseList().add(response(siteInformation));
                category = siteInformation.getCategory().getTitle();
                categoryList.remove(siteInformation.getCategory());
            }
            else {
                siteInformationApiResponseDTO.getSiteInformationApiResponseList().add(response(siteInformation));
            }
        }
        siteInformationApiResponseDTOList.add(siteInformationApiResponseDTO);
        for(InformationCategory _category : categoryList) {
            siteInformationApiResponseDTO = new SiteInformationApiResponseDTO();
            siteInformationApiResponseDTO.setCategory(_category);
            siteInformationApiResponseDTOList.add(siteInformationApiResponseDTO);

        }
        return Header.OK(siteInformationApiResponseDTOList);
    }

    @Transactional
    public Header<SiteInformationApiResponse> update(SiteInformationApiRequestDTO request) {
        SiteInformationApiRequest siteInformationApiRequest = request.getSiteInformationApiRequest();
        MultipartFile[] files = request.getFiles();

        SiteInformation siteInformation = siteInformationRepository.findById(siteInformationApiRequest.getId()).orElseThrow(
                () -> new SiteInformationNotFoundException(siteInformationApiRequest.getId()));

        siteInformation.getFileList().stream().forEach(file -> fileRepository.delete(file));
        List<FileApiResponse> fileApiResponseList = fileApiLogicService.uploadFiles(files, siteInformation.getId(), UploadCategory.SITE);
        siteInformation.setName(siteInformationApiRequest.getName())
                .setContent(siteInformationApiRequest.getContent())
                .setLinkUrl(siteInformationApiRequest.getLinkUrl())
                .setPublishStatus(siteInformationApiRequest.getPublishStatus())
                .setCategory(siteInformationApiRequest.getCategory());
        siteInformationRepository.save(siteInformation);

        return Header.OK(response(siteInformation, fileApiResponseList));
    }

    public Header delete(Long id) {
        SiteInformation siteInformation = siteInformationRepository.findById(id).orElseThrow(() -> new SiteInformationNotFoundException(id));
        siteInformationRepository.delete(siteInformation);
        return Header.OK();
    }

    private SiteInformationApiResponse response(SiteInformation siteInformation) {
        SiteInformationApiResponse siteInformationApiResponse = SiteInformationApiResponse.builder()
                .id(siteInformation.getId())
                .name(siteInformation.getName())
                .content(siteInformation.getContent())
                .linkUrl(siteInformation.getLinkUrl())
                .publishStatus(siteInformation.getPublishStatus())
                .category(siteInformation.getCategory())
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
                .category(siteInformation.getCategory())
                .createdAt(siteInformation.getCreatedAt())
                .createdBy(siteInformation.getCreatedBy())
                .updatedAt(siteInformation.getUpdatedAt())
                .updatedBy(siteInformation.getUpdatedBy())
                .fileApiResponseList(fileList)
                .build();

        return siteInformationApiResponse;
    }
}
