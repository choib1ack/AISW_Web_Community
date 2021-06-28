package com.aisw.community.service.admin;

import com.aisw.community.advice.exception.CiteInformationNotFoundException;
import com.aisw.community.model.entity.admin.CiteInformation;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.admin.CiteInformationApiRequest;
import com.aisw.community.model.network.response.admin.BannerApiResponse;
import com.aisw.community.model.network.response.admin.CiteInformationApiResponse;
import com.aisw.community.repository.admin.CiteInformationRepository;
import com.aisw.community.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CiteInformationApiLogicService extends BaseService<CiteInformationApiRequest, CiteInformationApiResponse, CiteInformation> {

    @Autowired
    private CiteInformationRepository citeInformationRepository;

    @Override
    public Header<CiteInformationApiResponse> create(Header<CiteInformationApiRequest> request) {
        CiteInformationApiRequest citeInformationApiRequest = request.getData();

        CiteInformation citeInformation = CiteInformation.builder()
                .name(citeInformationApiRequest.getName())
                .content(citeInformationApiRequest.getContent())
                .linkUrl(citeInformationApiRequest.getLinkUrl())
                .publishStatus(citeInformationApiRequest.getPublishStatus())
                .informationCategory(citeInformationApiRequest.getInformationCategory())
                .build();

        CiteInformation newCiteInformation = baseRepository.save(citeInformation);

        return Header.OK(response(newCiteInformation));
    }

    @Override
    public Header<CiteInformationApiResponse> read(Long id) {
        return null;
    }

    @Override
    public Header<CiteInformationApiResponse> update(Header<CiteInformationApiRequest> request) {
        CiteInformationApiRequest citeInformationApiRequest = request.getData();

        CiteInformation citeInformation = baseRepository.findById(citeInformationApiRequest.getId()).orElseThrow(
                () -> new CiteInformationNotFoundException(citeInformationApiRequest.getId()));

        citeInformation.setName(citeInformationApiRequest.getName())
                .setContent(citeInformationApiRequest.getContent())
                .setLinkUrl(citeInformationApiRequest.getLinkUrl())
                .setPublishStatus(citeInformationApiRequest.getPublishStatus());
        baseRepository.save(citeInformation);

        return Header.OK(response(citeInformation));
    }

    @Override
    public Header delete(Long id, Long userId) {
        return null;
    }

    public Header delete(Long id) {
        CiteInformation citeInformation = baseRepository.findById(id).orElseThrow(() -> new CiteInformationNotFoundException(id));
        baseRepository.delete(citeInformation);
        return Header.OK();
    }

    public Header<List<CiteInformationApiResponse>> readCite() {
        List<CiteInformation> citeInformationList = citeInformationRepository.findAllByPublishStatus(Boolean.TRUE);

        List<CiteInformationApiResponse> citeInformationApiResponseList = citeInformationList.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(citeInformationApiResponseList);
    }

    private CiteInformationApiResponse response(CiteInformation citeInformation) {
        CiteInformationApiResponse citeInformationApiResponse = CiteInformationApiResponse.builder()
                .id(citeInformation.getId())
                .name(citeInformation.getName())
                .content(citeInformation.getContent())
                .linkUrl(citeInformation.getLinkUrl())
                .publishStatus(citeInformation.getPublishStatus())
                .informationCategory(citeInformation.getInformationCategory())
                .createdAt(citeInformation.getCreatedAt())
                .createdBy(citeInformation.getCreatedBy())
                .updatedAt(citeInformation.getUpdatedAt())
                .updatedBy(citeInformation.getUpdatedBy())
                .build();

        return citeInformationApiResponse;
    }

    @Override
    public Header<List<CiteInformationApiResponse>> search(Pageable pageable) {
        return null;
    }
}
