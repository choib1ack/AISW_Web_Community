package com.aisw.community.service;

import com.aisw.community.model.entity.Council;
import com.aisw.community.model.entity.University;
import com.aisw.community.model.enumclass.NoticeCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.CouncilApiRequest;
import com.aisw.community.model.network.request.NoticeApiRequest;
import com.aisw.community.model.network.response.CouncilApiResponse;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.model.network.response.UniversityApiResponse;
import com.aisw.community.repository.CouncilRepository;
import com.aisw.community.repository.NoticeRepository;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouncilApiLogicService extends PostService<CouncilApiRequest, CouncilApiResponse, Council> {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouncilRepository councilRepository;

    @Autowired
    private NoticeApiLogicService noticeApiLogicService;

    @Override
    public Header<CouncilApiResponse> create(Header<CouncilApiRequest> request) {
        CouncilApiRequest councilApiRequest = request.getData();

        NoticeApiRequest noticeApiRequest = NoticeApiRequest.builder().category(NoticeCategory.COUNCIL).build();
        NoticeApiResponse noticeApiResponse = noticeApiLogicService.create(Header.OK(noticeApiRequest)).getData();

        Council council = Council.builder()
                .title(councilApiRequest.getTitle())
                .content(councilApiRequest.getContent())
                .attachmentFile(councilApiRequest.getAttachmentFile())
                .status(councilApiRequest.getStatus())
                .views(councilApiRequest.getViews())
                .level(councilApiRequest.getLevel())
                .user(userRepository.getOne(councilApiRequest.getUserId()))
                .notice(noticeRepository.getOne(noticeApiResponse.getId()))
                .build();

        Council newCouncil = baseRepository.save(council);
        return Header.OK(response(newCouncil));
    }

    @Override
    public Header<CouncilApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(council -> {
                    CouncilApiRequest councilApiRequest = CouncilApiRequest.builder()
                            .id(council.getId())
                            .title(council.getTitle())
                            .content(council.getContent())
                            .attachmentFile(council.getAttachmentFile())
                            .status(council.getStatus())
                            .views(council.getViews() + 1)
                            .level(council.getLevel())
                            .build();
                    return update(Header.OK(councilApiRequest));
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    @Transactional
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
                            .setLevel(councilApiRequest.getLevel());

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
                    noticeRepository.findById(council.getNotice().getId())
                            .map(notice -> {
                                noticeRepository.delete(notice);
                                return Header.OK();
                            })
                            .orElseGet(() -> Header.ERROR("데이터 없음"));
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
                .userId(council.getUser().getId())
                .noticeId(council.getNotice().getId())
                .build();

        return councilApiResponse;
    }

    @Override
    public Header<List<CouncilApiResponse>> search(Pageable pageable) {
        Page<Council> councils = baseRepository.findAll(pageable);

        return getListHeader(councils);
    }

    @Override
    public Header<List<CouncilApiResponse>> searchByWriter(String writer, Pageable pageable) {
        Page<Council> councils = councilRepository.findAllByCreatedByContaining(writer, pageable);

        return getListHeader(councils);
    }

    @Override
    public Header<List<CouncilApiResponse>> searchByTitle(String title, Pageable pageable) {
        Page<Council> councils = councilRepository.findAllByTitleContaining(title, pageable);

        return getListHeader(councils);
    }

    @Override
    public Header<List<CouncilApiResponse>> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Council> councils = councilRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);

        return getListHeader(councils);
    }

    private Header<List<CouncilApiResponse>> getListHeader(Page<Council> councils) {
        List<CouncilApiResponse> councilApiResponseList = councils.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(councils.getTotalElements())
                .totalPages(councils.getTotalPages())
                .currentElements(councils.getNumberOfElements())
                .currentPage(councils.getNumber())
                .build();

        return Header.OK(councilApiResponseList, pagination);
    }
}
