package com.aisw.community.service;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.Council;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.CouncilApiRequest;
import com.aisw.community.model.network.response.CouncilApiResponse;
import com.aisw.community.repository.CouncilRepository;
import com.aisw.community.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouncilApiLogicService implements CrudInterface<CouncilApiRequest, CouncilApiResponse> {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private CouncilRepository councilRepository;

    @Override
    public Header<CouncilApiResponse> create(Header<CouncilApiRequest> request) {
        CouncilApiRequest councilApiRequest = request.getData();

        Council council = Council.builder()
                .title(councilApiRequest.getTitle())
                .content(councilApiRequest.getContent())
                .attachmentFile(councilApiRequest.getAttachmentFile())
                .status(councilApiRequest.getStatus())
                .views(councilApiRequest.getViews())
                .level(councilApiRequest.getLevel())
                .notice(noticeRepository.getOne(councilApiRequest.getNoticeId()))
                .build();

        Council newCouncil = councilRepository.save(council);
        return response(newCouncil);
    }

    @Override
    public Header<CouncilApiResponse> read(Long id) {
        return councilRepository.findById(id)
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<CouncilApiResponse> update(Header<CouncilApiRequest> request) {
        CouncilApiRequest councilApiRequest = request.getData();

        return councilRepository.findById(councilApiRequest.getId())
                .map(council -> {
                    council
                            .setTitle(councilApiRequest.getTitle())
                            .setContent(councilApiRequest.getContent())
                            .setAttachmentFile(councilApiRequest.getAttachmentFile())
                            .setStatus(councilApiRequest.getStatus())
                            .setViews(councilApiRequest.getViews())
                            .setLevel(councilApiRequest.getLevel())
                            .setNotice(noticeRepository.getOne(councilApiRequest.getNoticeId()));

                    return council;
                })
                .map(council -> councilRepository.save(council))
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return councilRepository.findById(id)
                .map(council -> {
                    councilRepository.delete(council);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private Header<CouncilApiResponse> response(Council council) {
        CouncilApiResponse councilApiResponse = CouncilApiResponse.builder()
                .id(council.getId())
                .title(council.getTitle())
                .content(council.getContent())
                .attachmentFile(council.getAttachmentFile())
                .status(council.getStatus())
                .createdAt(council.getCreatedAt())
                .createdBy(council.getCreatedBy())
                .updatedAt(council.getUpdatedAt())
                .updatedBy(council.getUpdatedBy())
                .views(council.getViews())
                .level(council.getLevel())
                .noticeId(council.getNotice().getId())
                .build();

        return Header.OK(councilApiResponse);
    }
}
