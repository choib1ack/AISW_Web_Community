package com.aisw.community.service;

import com.aisw.community.model.entity.*;
import com.aisw.community.model.enumclass.NoticeCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.NoticeApiRequest;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.model.network.response.BoardListApiResponse;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.model.network.response.NoticeListApiResponse;
import com.aisw.community.repository.CouncilRepository;
import com.aisw.community.repository.DepartmentRepository;
import com.aisw.community.repository.NoticeRepository;
import com.aisw.community.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeApiLogicService extends BoardService<NoticeApiRequest, NoticeListApiResponse, NoticeApiResponse, Notice> {

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CouncilRepository councilRepository;

    @Override
    public Header<NoticeApiResponse> create(Header<NoticeApiRequest> request) {
        NoticeApiRequest noticeApiRequest = request.getData();
        Notice notice = Notice.builder().category(noticeApiRequest.getCategory()).build();
        Notice newNotice = baseRepository.save(notice);

        return Header.OK(response(newNotice));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(notice -> {
                    baseRepository.delete(notice);
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

    @Override
    public Header<List<NoticeListApiResponse>> searchList(Pageable pageable) {
        Page<Notice> notices = baseRepository.findAll(pageable);

        List<NoticeListApiResponse> noticeListApiResponseList = new ArrayList<>();

        notices.stream().map(notice -> {
            if(notice.getCategory() == NoticeCategory.UNIVERSITY) {
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
            }
            else if(notice.getCategory() == NoticeCategory.DEPARTMENT) {
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
            }
            else if(notice.getCategory() == NoticeCategory.COUNCIL) {
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
            }
            return noticeListApiResponseList;
        })
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(notices.getTotalElements())
                .totalPages(notices.getTotalPages())
                .currentElements(notices.getNumberOfElements())
                .currentPage(notices.getNumber())
                .build();

        return Header.OK(noticeListApiResponseList, pagination);
    }

    @Override
    public Header<List<NoticeListApiResponse>> searchByWriter(String writer, Pageable pageable) {
        List<University> universityList = universityRepository.findAllByCreatedByContaining(writer);
        List<Department> departmentList = departmentRepository.findAllByCreatedByContaining(writer);
        List<Council> councilList = councilRepository.findAllByCreatedByContaining(writer);

        List<NoticeListApiResponse> noticeListApiResponseList = new ArrayList<>();
        universityList.stream().forEach(university -> {
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
        departmentList.stream().forEach(department -> {
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
        councilList.stream().forEach(council -> {
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

        Page<NoticeListApiResponse> page =
                new PageImpl<>(noticeListApiResponseList, pageable, noticeListApiResponseList.size());
        Pagination pagination = Pagination.builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentElements(page.getNumberOfElements())
                .currentPage(page.getNumber())
                .build();

        return Header.OK(noticeListApiResponseList, pagination);
    }

    @Override
    public Header<List<NoticeListApiResponse>> searchByTitle(String title, Pageable pageable) {
        return null;
    }

    @Override
    public Header<List<NoticeListApiResponse>> searchByTitleOrContent(String title, String content, Pageable pageable) {
        return null;
    }
}
