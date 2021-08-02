package com.aisw.community.service.post.notice;

import com.aisw.community.advice.exception.NotEqualAccountException;
import com.aisw.community.advice.exception.PostNotFoundException;
import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.post.notice.University;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.post.notice.FileUploadToUniversityDTO;
import com.aisw.community.model.network.request.post.notice.UniversityApiRequest;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.model.network.response.post.notice.UniversityApiResponse;
import com.aisw.community.repository.post.file.FileRepository;
import com.aisw.community.repository.post.notice.UniversityRepository;
import com.aisw.community.repository.user.UserRepository;
import com.aisw.community.service.post.file.FileApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityApiLogicService extends NoticePostService<UniversityApiRequest, FileUploadToUniversityDTO, NoticeResponseDTO, UniversityApiResponse, University> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileApiLogicService fileApiLogicService;

    @Override
    public Header<UniversityApiResponse> create(Header<UniversityApiRequest> request) {
        UniversityApiRequest universityApiRequest = request.getData();
        User user = userRepository.findById(universityApiRequest.getAccountId()).orElseThrow(
                () -> new UserNotFoundException(universityApiRequest.getAccountId()));
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
    public Header<UniversityApiResponse> create(FileUploadToUniversityDTO request) {
        UniversityApiRequest universityApiRequest = request.getUniversityApiRequest();

        User user = userRepository.findById(universityApiRequest.getAccountId()).orElseThrow(
                () -> new UserNotFoundException(universityApiRequest.getAccountId()));
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
        List<FileApiResponse> fileApiResponseList = fileApiLogicService.uploadFiles(files, newUniversity.getId(), UploadCategory.POST);

        return Header.OK(response(newUniversity, fileApiResponseList));
    }

    @Override
    @Transactional
    public Header<UniversityApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(university -> university.setViews(university.getViews() + 1))
                .map(university -> baseRepository.save((University) university))
                .map(this::response)
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Transactional
    public Header<UniversityApiResponse> update(Header<UniversityApiRequest> request) {
        UniversityApiRequest universityApiRequest = request.getData();

        University university = baseRepository.findById(universityApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(universityApiRequest.getId()));

        if(university.getUser().getId() != universityApiRequest.getAccountId()) {
            throw new NotEqualAccountException(universityApiRequest.getAccountId());
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
    public Header<UniversityApiResponse> update(FileUploadToUniversityDTO request) {
        UniversityApiRequest universityApiRequest = request.getUniversityApiRequest();
        MultipartFile[] files = request.getFiles();

        University university = baseRepository.findById(universityApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(universityApiRequest.getId()));

        if(university.getUser().getId() != universityApiRequest.getAccountId()) {
            throw new NotEqualAccountException(universityApiRequest.getAccountId());
        }

        university.getFileList().stream().forEach(file -> fileRepository.delete(file));
        university.getFileList().clear();
        List<FileApiResponse> fileApiResponseList = fileApiLogicService.uploadFiles(files, university.getId(), UploadCategory.POST);

        university
                .setTitle(universityApiRequest.getTitle())
                .setContent(universityApiRequest.getContent())
                .setStatus(universityApiRequest.getStatus());
        university.setCampus(universityApiRequest.getCampus());
        baseRepository.save(university);

        return Header.OK(response(university, fileApiResponseList));
    }

    @Override
    public Header delete(Long id, Long userId) {
        University university = baseRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        if (university.getUser().getId() != userId) {
            throw new NotEqualAccountException(userId);
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
                .accountId(university.getUser().getId())
                .fileApiResponseList(university.getFileList().stream()
                        .map(file -> fileApiLogicService.response(file)).collect(Collectors.toList()))
                .build();

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
                .accountId(university.getUser().getId())
                .fileApiResponseList(fileApiResponseList)
                .build();

        return universityApiResponse;
    }

    @Override
    public Header<NoticeResponseDTO> search(Pageable pageable) {
        Page<University> universities = baseRepository.findAll(pageable);
        Page<University> universitiesByStatus = searchByStatus(pageable);

        return getListHeader(universities, universitiesByStatus);
    }

    @Override
    public Header<NoticeResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<University> universities = universityRepository.findAllByWriterContaining(writer, pageable);
        Page<University> universitiesByStatus = searchByStatus(pageable);

        return getListHeader(universities, universitiesByStatus);
    }

    @Override
    public Header<NoticeResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<University> universities = universityRepository.findAllByTitleContaining(title, pageable);
        Page<University> universitiesByStatus = searchByStatus(pageable);

        return getListHeader(universities, universitiesByStatus);
    }

    @Override
    public Header<NoticeResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<University> universities = universityRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<University> universitiesByStatus = searchByStatus(pageable);

        return getListHeader(universities, universitiesByStatus);
    }

    public Page<University> searchByStatus(Pageable pageable) {
        Page<University> universities = universityRepository.findAllByStatusOrStatus(
                BulletinStatus.URGENT, BulletinStatus.NOTICE, pageable);

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
