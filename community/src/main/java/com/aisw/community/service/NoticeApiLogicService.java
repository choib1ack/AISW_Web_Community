package com.aisw.community.service;

import com.aisw.community.model.entity.Notice;
import com.aisw.community.model.enumclass.NoticeCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.model.network.response.NoticeListApiResponse;
import com.aisw.community.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeApiLogicService {

    @Autowired
    private NoticeRepository noticeRepository;

    public Header<NoticeApiResponse> create() {
        Notice notice = Notice.builder().build();
        Notice newNotice = noticeRepository.save(notice);

        return Header.OK(response(newNotice));
    }

    public Header delete(Long id) {
        return noticeRepository.findById(id)
                .map(notice -> {
                    noticeRepository.delete(notice);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private NoticeApiResponse response(Notice notice) {
        NoticeApiResponse noticeApiResponse = NoticeApiResponse.builder()
                .id(notice.getId())
                .cratedAt(notice.getCreatedAt())
                .build();

        return noticeApiResponse;
    }

    public Header<List<NoticeListApiResponse>> searchList(Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAll(pageable);

        List<NoticeListApiResponse> noticeListApiResponseList = new ArrayList<>();

        notices.stream().map(notice -> {
            notice.getUniversityList().stream().forEach(university -> {
                NoticeListApiResponse noticeListApiResponse = NoticeListApiResponse.builder()
                        .id(university.getId())
                        .category(NoticeCategory.UNIVERSITY)
                        .title(university.getTitle())
                        .status(university.getStatus())
                        .createdBy(university.getCreatedBy())
                        .createdAt(university.getCreatedAt())
                        .views(university.getViews())
                        .build();

                noticeListApiResponseList.add(noticeListApiResponse);
            });
            notice.getDepartmentList().stream().forEach(department -> {
                NoticeListApiResponse noticeListApiResponse = NoticeListApiResponse.builder()
                        .id(department.getId())
                        .category(NoticeCategory.DEPARTMENT)
                        .title(department.getTitle())
                        .status(department.getStatus())
                        .createdBy(department.getCreatedBy())
                        .createdAt(department.getCreatedAt())
                        .views(department.getViews())
                        .build();

                noticeListApiResponseList.add(noticeListApiResponse);
            });

            notice.getCouncilList().stream().forEach(council -> {
                NoticeListApiResponse noticeListApiResponse = NoticeListApiResponse.builder()
                        .id(council.getId())
                        .category(NoticeCategory.COUNCIL)
                        .title(council.getTitle())
                        .status(council.getStatus())
                        .createdBy(council.getCreatedBy())
                        .createdAt(council.getCreatedAt())
                        .views(council.getViews())
                        .build();

                noticeListApiResponseList.add(noticeListApiResponse);
            });

            return noticeListApiResponseList;
        })
                .collect(Collectors.toList());

        return Header.OK(noticeListApiResponseList);
    }
}
