package com.aisw.community.service;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.Board;
import com.aisw.community.model.entity.Council;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.CouncilApiRequest;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.model.network.response.CouncilApiResponse;
import com.aisw.community.repository.CouncilRepository;
import com.aisw.community.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouncilApiLogicService extends BaseService<CouncilApiRequest, CouncilApiResponse, Council> {

    @Autowired
    private NoticeRepository noticeRepository;

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

        Council newCouncil = baseRepository.save(council);
        return Header.OK(response(newCouncil));
    }

    @Override
    public Header<CouncilApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<CouncilApiResponse> update(Header<CouncilApiRequest> request) {
        CouncilApiRequest councilApiRequest = request.getData();

        return baseRepository.findById(councilApiRequest.getId())
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
                .map(council -> baseRepository.save(council))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(council -> {
                    baseRepository.delete(council);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private CouncilApiResponse response(Council council) {
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

        return councilApiResponse;
    }

    @Override
    public Header<List<CouncilApiResponse>> search(Pageable pageable) {
        Page<Council> councils = baseRepository.findAll(pageable);

        List<CouncilApiResponse> councilApiResponseList = councils.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(councilApiResponseList);
    }
}
