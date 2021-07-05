package com.aisw.community.service.admin;

import com.aisw.community.advice.exception.SiteInformationNotFoundException;
import com.aisw.community.model.entity.admin.SiteInformation;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.SiteInformationApiRequest;
import com.aisw.community.model.network.response.admin.SiteInformationApiResponse;
import com.aisw.community.model.network.response.admin.SiteInformationApiResponseDTO;
import com.aisw.community.repository.admin.SiteInformationRepository;
import com.aisw.community.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SiteInformationApiLogicService extends BaseService<SiteInformationApiRequest, SiteInformationApiResponse, SiteInformation> {

    @Autowired
    private SiteInformationRepository siteInformationRepository;

    @Override
    public Header<SiteInformationApiResponse> create(Header<SiteInformationApiRequest> request) {
        SiteInformationApiRequest siteInformationApiRequest = request.getData();

        SiteInformation siteInformation = SiteInformation.builder()
                .name(siteInformationApiRequest.getName())
                .content(siteInformationApiRequest.getContent())
                .linkUrl(siteInformationApiRequest.getLinkUrl())
                .publishStatus(siteInformationApiRequest.getPublishStatus())
                .category(siteInformationApiRequest.getCategory())
                .build();

        SiteInformation newSiteInformation = baseRepository.save(siteInformation);

        return Header.OK(response(newSiteInformation));
    }

    @Override
    public Header<SiteInformationApiResponse> read(Long id) {
        return null;
    }

    @Override
    public Header<SiteInformationApiResponse> update(Header<SiteInformationApiRequest> request) {
        SiteInformationApiRequest siteInformationApiRequest = request.getData();

        SiteInformation siteInformation = baseRepository.findById(siteInformationApiRequest.getId()).orElseThrow(
                () -> new SiteInformationNotFoundException(siteInformationApiRequest.getId()));

        siteInformation.setName(siteInformationApiRequest.getName())
                .setContent(siteInformationApiRequest.getContent())
                .setLinkUrl(siteInformationApiRequest.getLinkUrl())
                .setPublishStatus(siteInformationApiRequest.getPublishStatus());
        baseRepository.save(siteInformation);

        return Header.OK(response(siteInformation));
    }

    @Override
    public Header delete(Long id, Long userId) {
        return null;
    }

    public Header delete(Long id) {
        SiteInformation siteInformation = baseRepository.findById(id).orElseThrow(() -> new SiteInformationNotFoundException(id));
        baseRepository.delete(siteInformation);
        return Header.OK();
    }

    public Header<List<SiteInformationApiResponseDTO>> readSite() {
        List<SiteInformation> siteInformationList = siteInformationRepository.findAllByPublishStatus(Boolean.TRUE);
        Collections.sort(siteInformationList, Comparator.comparing(siteInformation -> siteInformation.getCategory().getId()));

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
            }
            else {
                siteInformationApiResponseDTO.getSiteInformationApiResponseList().add(response(siteInformation));
            }
        }
        siteInformationApiResponseDTOList.add(siteInformationApiResponseDTO);

        return Header.OK(siteInformationApiResponseDTOList);
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
                .fileSet(siteInformation.getFile())
                .build();

        return siteInformationApiResponse;
    }

    @Override
    public Header<List<SiteInformationApiResponse>> search(Pageable pageable) {
        return null;
    }
}
