package com.aisw.community.service;

import com.aisw.community.controller.CrudController;
import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.Notice;
import com.aisw.community.model.entity.University;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.NoticeApiRequest;
import com.aisw.community.model.network.request.UniversityApiRequest;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.model.network.response.UniversityApiResponse;
import com.aisw.community.repository.NoticeRepository;
import com.aisw.community.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityApiLogicService extends BaseService<UniversityApiRequest, UniversityApiResponse, University> {

    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public Header<UniversityApiResponse> create(Header<UniversityApiRequest> request) {
        UniversityApiRequest universityApiRequest = request.getData();

        University university = University.builder()
                .title(universityApiRequest.getTitle())
                .content(universityApiRequest.getContent())
                .attachmentFile(universityApiRequest.getAttachmentFile())
                .status(universityApiRequest.getStatus())
                .views(universityApiRequest.getViews())
                .level(universityApiRequest.getLevel())
                .campus(universityApiRequest.getCampus())
                .notice(noticeRepository.getOne(universityApiRequest.getNoticeId()))
                .build();

        University newUniversity = baseRepository.save(university);
        return response(newUniversity);
    }

    @Override
    public Header<UniversityApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<UniversityApiResponse> update(Header<UniversityApiRequest> request) {
        UniversityApiRequest universityApiRequest = request.getData();

        return baseRepository.findById(universityApiRequest.getId())
                .map(university -> {
                    university
                            .setTitle(universityApiRequest.getTitle())
                            .setContent(universityApiRequest.getContent())
                            .setAttachmentFile(universityApiRequest.getAttachmentFile())
                            .setStatus(universityApiRequest.getStatus())
                            .setViews(universityApiRequest.getViews())
                            .setLevel(universityApiRequest.getLevel())
                            .setCampus(universityApiRequest.getCampus())
                            .setNotice(noticeRepository.getOne(universityApiRequest.getNoticeId()));

                    return university;
                })
                .map(university -> baseRepository.save(university))
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(university -> {
                    baseRepository.delete(university);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private Header<UniversityApiResponse> response(University university) {
        UniversityApiResponse universityApiResponse = UniversityApiResponse.builder()
                .id(university.getId())
                .title(university.getTitle())
                .content(university.getContent())
                .attachmentFile(university.getAttachmentFile())
                .status(university.getStatus())
                .createdAt(university.getCreatedAt())
                .createdBy(university.getCreatedBy())
                .updatedAt(university.getUpdatedAt())
                .updatedBy(university.getUpdatedBy())
                .views(university.getViews())
                .level(university.getLevel())
                .campus(university.getCampus())
                .noticeId(university.getNotice().getId())
                .build();

        return Header.OK(universityApiResponse);
    }
}
