package com.aisw.community.service.post.notice;

import com.aisw.community.model.entity.post.notice.Notice;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.response.post.notice.NoticeApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.repository.post.notice.NoticeRepository;
import com.aisw.community.service.post.AbsBulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService extends AbsBulletinService<NoticeResponseDTO, Notice> {

    @Autowired
    private NoticeRepository noticeRepository;

    @Cacheable(value = "noticeReadAll", key = "#pageable.pageNumber")
    public Header<NoticeResponseDTO> readAll(Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAll(pageable);
        List<Notice> noticesByStatus = searchByStatus();

        return response(notices, noticesByStatus);
    }

    @Override
    @Cacheable(value = "noticeSearchByWriter",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#writer, #pageable.pageNumber)")
    public Header<NoticeResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAllByWriterContaining(writer, pageable);
        List<Notice> noticesByStatus = searchByStatus();

        return response(notices, noticesByStatus);
    }

    @Override
    @Cacheable(value = "noticeSearchByTitle",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #pageable.pageNumber)")
    public Header<NoticeResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAllByTitleContaining(title, pageable);
        List<Notice> noticesByStatus = searchByStatus();

        return response(notices, noticesByStatus);
    }

    @Override
    @Cacheable(value = "noticeSearchByTitleOrContent",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #content, #pageable.pageNumber)")
    public Header<NoticeResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAllByTitleContainingOrContentContaining(title, content, pageable);
        List<Notice> noticesByStatus = searchByStatus();

        return response(notices, noticesByStatus);
    }

    public List<Notice> searchByStatus() {
        return noticeRepository.findTop10ByStatusIn(Arrays.asList(BulletinStatus.URGENT, BulletinStatus.NOTICE));
    }

    private Header<NoticeResponseDTO> response(Page<Notice> notices, List<Notice> noticesByStatus) {
        NoticeResponseDTO noticeResponseDTO = NoticeResponseDTO.builder()
                .noticeApiResponseList(notices.stream()
                        .map(notice -> NoticeApiResponse.builder()
                                .id(notice.getId())
                                .title(notice.getTitle())
                                .category(notice.getCategory())
                                .createdAt(notice.getCreatedAt())
                                .status(notice.getStatus())
                                .views(notice.getViews())
                                .writer(notice.getWriter())
                                .hasFile((notice.getFileList().size() != 0) ? true : false)
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
                    .hasFile((notice.getFileList().size() != 0) ? true : false)
                    .build();
            if(noticeApiResponse.getStatus() == BulletinStatus.NOTICE) {
                noticeApiNoticeResponseList.add(noticeApiResponse);
            }
            else if(noticeApiResponse.getStatus() == BulletinStatus.URGENT) {
                noticeApiUrgentResponseList.add(noticeApiResponse);
            }
        });
        noticeResponseDTO.setNoticeApiNoticeResponseList(noticeApiNoticeResponseList);
        noticeResponseDTO.setNoticeApiUrgentResponseList(noticeApiUrgentResponseList);

        Pagination pagination = Pagination.builder()
                .totalElements(notices.getTotalElements())
                .totalPages(notices.getTotalPages())
                .currentElements(notices.getNumberOfElements())
                .currentPage(notices.getNumber())
                .build();

        return Header.OK(noticeResponseDTO, pagination);
    }

}
