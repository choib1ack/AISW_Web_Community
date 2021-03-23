package com.aisw.community.service;

import com.aisw.community.model.entity.Council;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.CouncilApiRequest;
import com.aisw.community.model.network.response.CouncilApiResponse;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.model.network.response.NoticeResponse;
import com.aisw.community.repository.CouncilRepository;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouncilApiLogicService extends PostService<CouncilApiRequest, NoticeResponse, CouncilApiResponse, Council> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouncilRepository councilRepository;

    @Override
    public Header<CouncilApiResponse> create(Header<CouncilApiRequest> request) {
        CouncilApiRequest councilApiRequest = request.getData();

        Council council = Council.builder()
                .title(councilApiRequest.getTitle())
                .writer(userRepository.getOne(councilApiRequest.getUserId()).getName())
                .content(councilApiRequest.getContent())
                .attachmentFile(councilApiRequest.getAttachmentFile())
                .status(councilApiRequest.getStatus())
                .views(0L)
                .level(councilApiRequest.getLevel())
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.COUNCIL)
                .account(userRepository.getOne(councilApiRequest.getUserId()))
                .build();

        Council newCouncil = baseRepository.save(council);
        return Header.OK(response(newCouncil));
    }

    @Override
    @Transactional
    public Header<CouncilApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(council -> council.setViews(council.getViews() + 1))
                .map(council -> baseRepository.save((Council)council))
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
                    baseRepository.delete(council);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private CouncilApiResponse response(Council council) {
        CouncilApiResponse councilApiResponse = CouncilApiResponse.builder()
                .id(council.getId())
                .title(council.getTitle())
                .writer(council.getWriter())
                .content(council.getContent())
                .attachmentFile(council.getAttachmentFile())
                .status(council.getStatus())
                .views(council.getViews())
                .level(council.getLevel())
                .category(council.getCategory())
                .createdAt(council.getCreatedAt())
                .createdBy(council.getCreatedBy())
                .updatedAt(council.getUpdatedAt())
                .updatedBy(council.getUpdatedBy())
                .userId(council.getAccount().getId())
                .build();

        return councilApiResponse;
    }

    @Override
    public Header<NoticeResponse> search(Pageable pageable) {
        Page<Council> councils = baseRepository.findAll(pageable);
        Page<Council> councilsByStatus = searchByStatus(pageable);

        return getListHeader(councils, councilsByStatus);
    }

    @Override
    public Header<NoticeResponse> searchByWriter(String writer, Pageable pageable) {
        Page<Council> councils = councilRepository.findAllByWriterContaining(writer, pageable);
        Page<Council> councilsByStatus = searchByStatus(pageable);

        return getListHeader(councils, councilsByStatus);
    }

    @Override
    public Header<NoticeResponse> searchByTitle(String title, Pageable pageable) {
        Page<Council> councils = councilRepository.findAllByTitleContaining(title, pageable);
        Page<Council> councilsByStatus = searchByStatus(pageable);

        return getListHeader(councils, councilsByStatus);
    }

    @Override
    public Header<NoticeResponse> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Council> councils = councilRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<Council> councilsByStatus = searchByStatus(pageable);

        return getListHeader(councils, councilsByStatus);
    }

    public Page<Council> searchByStatus(Pageable pageable) {
        Page<Council> councils = councilRepository.findAllByStatusOrStatus(
                BulletinStatus.URGENT, BulletinStatus.NOTICE, pageable);

        return councils;
    }

    private Header<NoticeResponse> getListHeader
            (Page<Council> councils, Page<Council> councilsByStatus) {
        NoticeResponse noticeResponse = NoticeResponse.builder()
                .noticeApiResponseList(councils.stream()
                        .map(notice -> NoticeApiResponse.builder()
                                .id(notice.getId())
                                .title(notice.getTitle())
                                .category(notice.getCategory())
                                .createdAt(notice.getCreatedAt())
                                .status(notice.getStatus())
                                .views(notice.getViews())
                                .writer(notice.getWriter())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        List<NoticeApiResponse> noticeApiNoticeResponseList = new ArrayList<>();
        List<NoticeApiResponse> noticeApiUrgentResponseList = new ArrayList<>();
        councilsByStatus.stream().forEach(notice -> {
            NoticeApiResponse noticeApiResponse = NoticeApiResponse.builder()
                    .id(notice.getId())
                    .title(notice.getTitle())
                    .category(notice.getCategory())
                    .createdAt(notice.getCreatedAt())
                    .status(notice.getStatus())
                    .views(notice.getViews())
                    .writer(notice.getWriter())
                    .build();
            if(noticeApiResponse.getStatus() == BulletinStatus.NOTICE) {
                noticeApiNoticeResponseList.add(noticeApiResponse);
            }
            else if(noticeApiResponse.getStatus() == BulletinStatus.URGENT) {
                noticeApiUrgentResponseList.add(noticeApiResponse);
            }
        });
        noticeResponse.setNoticeApiNoticeResponseList(noticeApiNoticeResponseList);
        noticeResponse.setNoticeApiUrgentResponseList(noticeApiUrgentResponseList);

        Pagination pagination = Pagination.builder()
                .totalElements(councils.getTotalElements())
                .totalPages(councils.getTotalPages())
                .currentElements(councils.getNumberOfElements())
                .currentPage(councils.getNumber())
                .build();

        return Header.OK(noticeResponse, pagination);
    }
}
