package com.aisw.community.service;

import com.aisw.community.model.entity.Notice;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeApiLogicService extends BulletinService<NoticeApiResponse, Notice> {

    @Autowired
    private NoticeRepository noticeRepository;

    public Header<List<NoticeApiResponse>> searchList(Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAll(pageable);

        return getListHeader(notices);
    }

    @Override
    public Header<List<NoticeApiResponse>> searchByWriter(String writer, Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAllByWriterContaining(writer, pageable);

        return getListHeader(notices);
    }

    @Override
    public Header<List<NoticeApiResponse>> searchByTitle(String title, Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAllByTitleContaining(title, pageable);

        return getListHeader(notices);
    }

    @Override
    public Header<List<NoticeApiResponse>> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAllByTitleContainingOrContentContaining(title, content, pageable);

        return getListHeader(notices);
    }

    private Header<List<NoticeApiResponse>> getListHeader(Page<Notice> notices) {
        List<NoticeApiResponse> noticeApiResponseList = notices.stream()
                .map(notice -> NoticeApiResponse.builder()
                        .id(notice.getId())
                        .title(notice.getTitle())
                        .category(notice.getCategory())
                        .createdAt(notice.getCreatedAt())
                        .status(notice.getStatus())
                        .views(notice.getViews())
                        .writer(notice.getWriter())
                        .build())
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(notices.getTotalElements())
                .totalPages(notices.getTotalPages())
                .currentElements(notices.getNumberOfElements())
                .currentPage(notices.getNumber())
                .build();

        return Header.OK(noticeApiResponseList, pagination);
    }
}
