package com.aisw.community.service.admin;

import com.aisw.community.advice.exception.CiteInformationNotFoundException;
import com.aisw.community.model.entity.admin.SiteInformation;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.SiteInformationApiRequest;
import com.aisw.community.model.network.response.admin.SiteInformationApiResponse;
import com.aisw.community.repository.admin.SiteInformationRepository;
import com.aisw.community.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .informationCategory(siteInformationApiRequest.getInformationCategory())
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
                () -> new CiteInformationNotFoundException(siteInformationApiRequest.getId()));

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
        SiteInformation siteInformation = baseRepository.findById(id).orElseThrow(() -> new CiteInformationNotFoundException(id));
        baseRepository.delete(siteInformation);
        return Header.OK();
    }

    public Header<List<SiteInformationApiResponse>> readCite() {
        List<SiteInformation> siteInformationList = siteInformationRepository.findAllByPublishStatus(Boolean.TRUE);

        List<SiteInformationApiResponse> siteInformationApiResponseList = siteInformationList.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(siteInformationApiResponseList);
    }

    private SiteInformationApiResponse response(SiteInformation siteInformation) {
        SiteInformationApiResponse siteInformationApiResponse = SiteInformationApiResponse.builder()
                .id(siteInformation.getId())
                .name(siteInformation.getName())
                .content(siteInformation.getContent())
                .linkUrl(siteInformation.getLinkUrl())
                .publishStatus(siteInformation.getPublishStatus())
                .informationCategory(siteInformation.getInformationCategory())
                .createdAt(siteInformation.getCreatedAt())
                .createdBy(siteInformation.getCreatedBy())
                .updatedAt(siteInformation.getUpdatedAt())
                .updatedBy(siteInformation.getUpdatedBy())
                .build();

        return siteInformationApiResponse;
    }

    @Override
    public Header<List<SiteInformationApiResponse>> search(Pageable pageable) {
        return null;
    }
}
