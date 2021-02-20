package com.aisw.community.service;

import com.aisw.community.model.entity.University;
import com.aisw.community.model.enumclass.NoticeCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.UniversityApiRequest;
import com.aisw.community.model.network.response.UniversityApiResponse;
import com.aisw.community.repository.UniversityRepository;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityApiLogicService extends PostService<UniversityApiRequest, UniversityApiResponse, University> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Override
    public Header<UniversityApiResponse> create(Header<UniversityApiRequest> request) {
        UniversityApiRequest universityApiRequest = request.getData();

        University university = University.builder()
                .title(universityApiRequest.getTitle())
                .writer(userRepository.getOne(universityApiRequest.getUserId()).getName())
                .content(universityApiRequest.getContent())
                .attachmentFile(universityApiRequest.getAttachmentFile())
                .status(universityApiRequest.getStatus())
                .views(0L)
                .level(universityApiRequest.getLevel())
                .campus(universityApiRequest.getCampus())
                .category(NoticeCategory.UNIVERSITY)
                .user(userRepository.getOne(universityApiRequest.getUserId()))
                .build();

        University newUniversity = baseRepository.save(university);
        return Header.OK(response(newUniversity));
    }

    @Override
    @Transactional
    public Header<UniversityApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(university -> university.setViews(university.getViews() + 1))
                .map(university -> baseRepository.save((University)university))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    @Transactional
    public Header<UniversityApiResponse> update(Header<UniversityApiRequest> request) {
        UniversityApiRequest universityApiRequest = request.getData();

        return baseRepository.findById(universityApiRequest.getId())
                .map(university -> {
                    university
                            .setTitle(universityApiRequest.getTitle())
                            .setContent(universityApiRequest.getContent())
                            .setAttachmentFile(universityApiRequest.getAttachmentFile())
                            .setStatus(universityApiRequest.getStatus())
                            .setLevel(universityApiRequest.getLevel());
                    university.setCampus(universityApiRequest.getCampus());
                    return university;
                })
                .map(university -> baseRepository.save(university))
                .map(this::response)
                .map(Header::OK)
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

    private UniversityApiResponse response(University university) {
        UniversityApiResponse universityApiResponse = UniversityApiResponse.builder()
                .id(university.getId())
                .title(university.getTitle())
                .writer(university.getWriter())
                .content(university.getContent())
                .attachmentFile(university.getAttachmentFile())
                .status(university.getStatus())
                .views(university.getViews())
                .level(university.getLevel())
                .campus(university.getCampus())
                .category(university.getCategory())
                .createdAt(university.getCreatedAt())
                .createdBy(university.getCreatedBy())
                .updatedAt(university.getUpdatedAt())
                .updatedBy(university.getUpdatedBy())
                .userId(university.getUser().getId())
                .build();

        return universityApiResponse;
    }

    @Override
    public Header<List<UniversityApiResponse>> search(Pageable pageable) {
        Page<University> universities = baseRepository.findAll(pageable);

        return getListHeader(universities);
    }

    @Override
    public Header<List<UniversityApiResponse>> searchByWriter(String writer, Pageable pageable) {
        Page<University> universities = universityRepository.findAllByWriterContaining(writer, pageable);

        return getListHeader(universities);
    }

    @Override
    public Header<List<UniversityApiResponse>> searchByTitle(String title, Pageable pageable) {
        Page<University> universities = universityRepository.findAllByTitleContaining(title, pageable);

        return getListHeader(universities);
    }

    @Override
    public Header<List<UniversityApiResponse>> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<University> universities = universityRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);

        return getListHeader(universities);
    }

    private Header<List<UniversityApiResponse>> getListHeader(Page<University> universities) {
        List<UniversityApiResponse> universityApiResponseList = universities.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(universities.getTotalElements())
                .totalPages(universities.getTotalPages())
                .currentElements(universities.getNumberOfElements())
                .currentPage(universities.getNumber())
                .build();

        return Header.OK(universityApiResponseList, pagination);
    }
}
