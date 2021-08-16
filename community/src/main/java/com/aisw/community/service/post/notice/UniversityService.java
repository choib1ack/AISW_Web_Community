package com.aisw.community.service.post.notice;

import com.aisw.community.component.advice.exception.NotEqualUserException;
import com.aisw.community.component.advice.exception.PostNotFoundException;
import com.aisw.community.component.advice.exception.PostStatusNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.entity.post.notice.University;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.post.notice.FileUploadToUniversityRequest;
import com.aisw.community.model.network.request.post.notice.UniversityApiRequest;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.model.network.response.post.notice.UniversityApiResponse;
import com.aisw.community.repository.post.file.FileRepository;
import com.aisw.community.repository.post.notice.UniversityRepository;
import com.aisw.community.service.post.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityService extends NoticePostService<UniversityApiRequest, FileUploadToUniversityRequest, NoticeResponseDTO, UniversityApiResponse, University> {

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "universityReadAll", allEntries = true),
            @CacheEvict(value = "universitySearchByWriter", allEntries = true),
            @CacheEvict(value = "universitySearchByTitle", allEntries = true),
            @CacheEvict(value = "universitySearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<UniversityApiResponse> create(Authentication authentication, Header<UniversityApiRequest> request) {
        UniversityApiRequest universityApiRequest = request.getData();
        if(universityApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(universityApiRequest.getStatus().getTitle());
        }

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        University university = University.builder()
                .title(universityApiRequest.getTitle())
                .writer(user.getName())
                .content(universityApiRequest.getContent())
                .status(universityApiRequest.getStatus())
                .campus(universityApiRequest.getCampus())
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.UNIVERSITY)
                .user(user)
                .build();

        University newUniversity = baseRepository.save(university);
        return Header.OK(response(newUniversity));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "universityReadAll", allEntries = true),
            @CacheEvict(value = "universitySearchByWriter", allEntries = true),
            @CacheEvict(value = "universitySearchByTitle", allEntries = true),
            @CacheEvict(value = "universitySearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<UniversityApiResponse> create(Authentication authentication, FileUploadToUniversityRequest request) {
        UniversityApiRequest universityApiRequest = request.getUniversityApiRequest();
        if(universityApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(universityApiRequest.getStatus().getTitle());
        }

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        University university = University.builder()
                .title(universityApiRequest.getTitle())
                .writer(user.getName())
                .content(universityApiRequest.getContent())
                .status(universityApiRequest.getStatus())
                .campus(universityApiRequest.getCampus())
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.UNIVERSITY)
                .user(user)
                .build();
        University newUniversity = baseRepository.save(university);

        MultipartFile[] files = request.getFiles();
        List<FileApiResponse> fileApiResponseList = fileService.uploadFiles(files, newUniversity.getId(), UploadCategory.POST);

        return Header.OK(response(newUniversity, fileApiResponseList));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "universityReadAll", allEntries = true),
            @CacheEvict(value = "universitySearchByWriter", allEntries = true),
            @CacheEvict(value = "universitySearchByTitle", allEntries = true),
            @CacheEvict(value = "universitySearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true)
    })
    public Header<UniversityApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(university -> university.setViews(university.getViews() + 1))
                .map(university -> baseRepository.save((University) university))
                .map(this::response)
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "universityReadAll", allEntries = true),
            @CacheEvict(value = "universitySearchByWriter", allEntries = true),
            @CacheEvict(value = "universitySearchByTitle", allEntries = true),
            @CacheEvict(value = "universitySearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<UniversityApiResponse> update(Authentication authentication, Header<UniversityApiRequest> request) {
        UniversityApiRequest universityApiRequest = request.getData();
        if(universityApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(universityApiRequest.getStatus().getTitle());
        }

        University university = baseRepository.findById(universityApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(universityApiRequest.getId()));

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if(university.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        university
                .setTitle(universityApiRequest.getTitle())
                .setContent(universityApiRequest.getContent())
                .setStatus(universityApiRequest.getStatus());
        university.setCampus(universityApiRequest.getCampus());
        baseRepository.save(university);

        return Header.OK(response(university));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "universityReadAll", allEntries = true),
            @CacheEvict(value = "universitySearchByWriter", allEntries = true),
            @CacheEvict(value = "universitySearchByTitle", allEntries = true),
            @CacheEvict(value = "universitySearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<UniversityApiResponse> update(Authentication authentication, FileUploadToUniversityRequest request) {
        UniversityApiRequest universityApiRequest = request.getUniversityApiRequest();
        if(universityApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(universityApiRequest.getStatus().getTitle());
        }

        University university = baseRepository.findById(universityApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(universityApiRequest.getId()));

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if(university.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        MultipartFile[] files = request.getFiles();
        university.getFileList().stream().forEach(file -> fileRepository.delete(file));
        university.getFileList().clear();
        List<FileApiResponse> fileApiResponseList = fileService.uploadFiles(files, university.getId(), UploadCategory.POST);

        university
                .setTitle(universityApiRequest.getTitle())
                .setContent(universityApiRequest.getContent())
                .setStatus(universityApiRequest.getStatus());
        university.setCampus(universityApiRequest.getCampus());
        baseRepository.save(university);

        return Header.OK(response(university, fileApiResponseList));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "universityReadAll", allEntries = true),
            @CacheEvict(value = "universitySearchByWriter", allEntries = true),
            @CacheEvict(value = "universitySearchByTitle", allEntries = true),
            @CacheEvict(value = "universitySearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header delete(Authentication authentication, Long id) {
        University university = baseRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if (university.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        baseRepository.delete(university);
        return Header.OK();
    }

    private UniversityApiResponse response(University university) {
        UniversityApiResponse universityApiResponse = UniversityApiResponse.builder()
                .id(university.getId())
                .title(university.getTitle())
                .writer(university.getWriter())
                .content(university.getContent())
                .status(university.getStatus())
                .views(university.getViews())
                .campus(university.getCampus())
                .category(university.getCategory())
                .createdAt(university.getCreatedAt())
                .createdBy(university.getCreatedBy())
                .updatedAt(university.getUpdatedAt())
                .updatedBy(university.getUpdatedBy())
                .build();
        if (university.getFileList() != null) {
            universityApiResponse.setFileApiResponseList(fileService.getFileList(university.getFileList(), UploadCategory.POST, university.getId()));
        }

        return universityApiResponse;
    }

    private UniversityApiResponse response(University university, List<FileApiResponse> fileApiResponseList) {
        UniversityApiResponse universityApiResponse = UniversityApiResponse.builder()
                .id(university.getId())
                .title(university.getTitle())
                .writer(university.getWriter())
                .content(university.getContent())
                .status(university.getStatus())
                .views(university.getViews())
                .campus(university.getCampus())
                .category(university.getCategory())
                .createdAt(university.getCreatedAt())
                .createdBy(university.getCreatedBy())
                .updatedAt(university.getUpdatedAt())
                .updatedBy(university.getUpdatedBy())
                .fileApiResponseList(fileApiResponseList)
                .build();

        return universityApiResponse;
    }

    @Override
    @Cacheable(value = "universityReadAll", key = "#pageable.pageNumber")
    public Header<NoticeResponseDTO> readAll(Pageable pageable) {
        Page<University> universities = baseRepository.findAll(pageable);
        Page<University> universitiesByStatus = searchByStatus(pageable);

        return getListHeader(universities, universitiesByStatus);
    }

    @Override
    @Cacheable(value = "universitySearchByWriter",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#writer, #pageable.pageNumber)")
    public Header<NoticeResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<University> universities = universityRepository.findAllByWriterContaining(writer, pageable);
        Page<University> universitiesByStatus = searchByStatus(pageable);

        return getListHeader(universities, universitiesByStatus);
    }

    @Override
    @Cacheable(value = "universitySearchByTitle",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #pageable.pageNumber)")
    public Header<NoticeResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<University> universities = universityRepository.findAllByTitleContaining(title, pageable);
        Page<University> universitiesByStatus = searchByStatus(pageable);

        return getListHeader(universities, universitiesByStatus);
    }

    @Override
    @Cacheable(value = "universitySearchByTitleOrContent",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #content, #pageable.pageNumber)")
    public Header<NoticeResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<University> universities = universityRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<University> universitiesByStatus = searchByStatus(pageable);

        return getListHeader(universities, universitiesByStatus);
    }

    public Page<University> searchByStatus(Pageable pageable) {
        Page<University> universities = universityRepository.findAllByStatusIn(
                Arrays.asList(BulletinStatus.URGENT, BulletinStatus.NOTICE), pageable);

        return universities;
    }

    private Header<NoticeResponseDTO> getListHeader
            (Page<University> universities, Page<University> universitiesByStatus) {
        NoticeResponseDTO noticeResponseDTO = NoticeResponseDTO.builder()
                .noticeApiResponseList(universities.stream()
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
        universitiesByStatus.stream().forEach(notice -> {
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
        noticeResponseDTO.setNoticeApiNoticeResponseList(noticeApiNoticeResponseList);
        noticeResponseDTO.setNoticeApiUrgentResponseList(noticeApiUrgentResponseList);

        Pagination pagination = Pagination.builder()
                .totalElements(universities.getTotalElements())
                .totalPages(universities.getTotalPages())
                .currentElements(universities.getNumberOfElements())
                .currentPage(universities.getNumber())
                .build();

        return Header.OK(noticeResponseDTO, pagination);
    }
}