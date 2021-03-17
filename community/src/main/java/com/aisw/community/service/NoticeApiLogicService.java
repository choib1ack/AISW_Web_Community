package com.aisw.community.service;

import com.aisw.community.model.entity.Notice;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.model.network.response.BoardResponse;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.model.network.response.NoticeResponse;
import com.aisw.community.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeApiLogicService extends BulletinService<NoticeResponse, Notice> {

    @Autowired
    private NoticeRepository noticeRepository;

    public Header<NoticeResponse> searchList(Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAll(pageable);
        Page<Notice> noticesByStatus = searchByStatus(pageable);

        return getListHeader(notices, noticesByStatus);
    }

    @Override
    public Header<NoticeResponse> searchByWriter(String writer, Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAllByWriterContaining(writer, pageable);
        Page<Notice> noticesByStatus = searchByStatus(pageable);

        return getListHeader(notices, noticesByStatus);
    }

    @Override
    public Header<NoticeResponse> searchByTitle(String title, Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAllByTitleContaining(title, pageable);
        Page<Notice> noticesByStatus = searchByStatus(pageable);

        return getListHeader(notices, noticesByStatus);
    }

    @Override
    public Header<NoticeResponse> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<Notice> noticesByStatus = searchByStatus(pageable);

        return getListHeader(notices, noticesByStatus);
    }

    public Page<Notice> searchByStatus(Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAllByStatusOrStatus(
                BulletinStatus.URGENT, BulletinStatus.NOTICE, pageable);

        return notices;
    }

    private Header<NoticeResponse> getListHeader
            (Page<Notice> notices, Page<Notice> noticesByStatus) {
        NoticeResponse noticeResponse = NoticeResponse.builder()
                .noticeApiResponseList(notices.stream()
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
        noticesByStatus.stream().forEach(notice -> {
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
                .totalElements(notices.getTotalElements())
                .totalPages(notices.getTotalPages())
                .currentElements(notices.getNumberOfElements())
                .currentPage(notices.getNumber())
                .build();

        return Header.OK(noticeResponse, pagination);
    }

}
