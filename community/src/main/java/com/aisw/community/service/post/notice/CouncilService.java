package com.aisw.community.service.post.notice;

import com.aisw.community.component.advice.exception.NotEqualUserException;
import com.aisw.community.component.advice.exception.PostNotFoundException;
import com.aisw.community.model.entity.post.file.File;
import com.aisw.community.model.entity.post.notice.Council;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.post.notice.CouncilApiRequest;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.model.network.response.post.notice.CouncilApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.repository.post.notice.CouncilRepository;
import com.aisw.community.service.post.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouncilService implements NoticePostService<CouncilApiRequest, CouncilApiResponse, NoticeResponseDTO> {

    @Autowired
    private CouncilRepository councilRepository;

    @Autowired
    private FileService fileService;

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "councilReadAll", allEntries = true),
            @CacheEvict(value = "councilSearchByWriter", allEntries = true),
            @CacheEvict(value = "councilSearchByTitle", allEntries = true),
            @CacheEvict(value = "councilSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<CouncilApiResponse> create(User user, CouncilApiRequest councilApiRequest, MultipartFile[] files) {
        Council council = Council.builder()
                .title(councilApiRequest.getTitle())
                .writer(user.getName())
                .content(councilApiRequest.getContent())
                .status(councilApiRequest.getStatus())
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.COUNCIL)
                .user(user)
                .build();
        Council newCouncil = councilRepository.save(council);

        if(files != null) {
            List<FileApiResponse> fileApiResponseList =
                    fileService.uploadFiles(files, user.getUsername(), "/auth-student/notice/council", newCouncil.getId(), UploadCategory.POST);

            return Header.OK(response(newCouncil, fileApiResponseList));
        } else {
            return Header.OK(response(newCouncil));
        }
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "councilReadAll", allEntries = true),
            @CacheEvict(value = "councilSearchByWriter", allEntries = true),
            @CacheEvict(value = "councilSearchByTitle", allEntries = true),
            @CacheEvict(value = "councilSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true)
    })
    public Header<CouncilApiResponse> read(User user, Long id) {
        return councilRepository.findById(id)
                .map(council -> council.setViews(council.getViews() + 1))
                .map(council -> councilRepository.save((Council) council))
                .map(council -> response(user, council))
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "councilReadAll", allEntries = true),
            @CacheEvict(value = "councilSearchByWriter", allEntries = true),
            @CacheEvict(value = "councilSearchByTitle", allEntries = true),
            @CacheEvict(value = "councilSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<CouncilApiResponse> update(User user, CouncilApiRequest councilApiRequest, MultipartFile[] files, List<Long> delFileIdList) {
        Council council = councilRepository.findById(councilApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(councilApiRequest.getId()));
        if (council.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        council
                .setTitle(councilApiRequest.getTitle())
                .setContent(councilApiRequest.getContent())
                .setStatus(councilApiRequest.getStatus());
        councilRepository.save(council);

        if(council.getFileList() != null && delFileIdList != null) {
            List<File> delFileList = new ArrayList<>();
            for(File file : council.getFileList()) {
                if(delFileIdList.contains(file.getId())) {
                    fileService.deleteFile(file);
                    delFileList.add(file);
                }
            }
            for (File file : delFileList) {
                council.getFileList().remove(file);
            }
        }
        if(files != null) {
            List<FileApiResponse> fileApiResponseList =
                    fileService.uploadFiles(files, user.getUsername(), "/auth-student/notice/council", council.getId(), UploadCategory.POST);
            return Header.OK(response(council, fileApiResponseList));
        } else {
            return Header.OK(response(council));
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "councilReadAll", allEntries = true),
            @CacheEvict(value = "councilSearchByWriter", allEntries = true),
            @CacheEvict(value = "councilSearchByTitle", allEntries = true),
            @CacheEvict(value = "councilSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header delete(User user, Long id) {
        Council council = councilRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        if (council.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        fileService.deleteFileList(council.getFileList());
        councilRepository.delete(council);
        return Header.OK();
    }

    private CouncilApiResponse response(Council council) {
        return CouncilApiResponse.builder()
                .id(council.getId())
                .title(council.getTitle())
                .writer(council.getWriter())
                .content(council.getContent())
                .status(council.getStatus())
                .views(council.getViews())
                .category(council.getCategory())
                .createdAt(council.getCreatedAt())
                .createdBy(council.getCreatedBy())
                .updatedAt(council.getUpdatedAt())
                .updatedBy(council.getUpdatedBy())
                .build();
    }

    private CouncilApiResponse response(User user, Council council) {
        CouncilApiResponse councilApiResponse = CouncilApiResponse.builder()
                .id(council.getId())
                .title(council.getTitle())
                .writer(council.getWriter())
                .content(council.getContent())
                .status(council.getStatus())
                .views(council.getViews())
                .category(council.getCategory())
                .createdAt(council.getCreatedAt())
                .createdBy(council.getCreatedBy())
                .updatedAt(council.getUpdatedAt())
                .updatedBy(council.getUpdatedBy())
                .isWriter((user.getId() == council.getUser().getId()) ? true : false)
                .build();
        if (council.getFileList() != null) {
            councilApiResponse.setFileApiResponseList(fileService.getFileList(council.getFileList()));
        }

        return councilApiResponse;
    }

    private CouncilApiResponse response(Council council, List<FileApiResponse> fileApiResponseList) {
        if(council.getFileList() != null) {
            fileApiResponseList.addAll(fileService.getFileList(council.getFileList()));
        }
        return CouncilApiResponse.builder()
                .id(council.getId())
                .title(council.getTitle())
                .writer(council.getWriter())
                .content(council.getContent())
                .status(council.getStatus())
                .views(council.getViews())
                .category(council.getCategory())
                .createdAt(council.getCreatedAt())
                .createdBy(council.getCreatedBy())
                .updatedAt(council.getUpdatedAt())
                .updatedBy(council.getUpdatedBy())
                .fileApiResponseList(fileApiResponseList)
                .build();
    }

    @Override
    @Cacheable(value = "councilReadAll", key = "#pageable.pageNumber")
    public Header<NoticeResponseDTO> readAll(Pageable pageable) {
        Page<Council> councils = councilRepository.findAll(pageable);
        List<Council> councilsByStatus = searchByStatus();

        return response(councils, councilsByStatus);
    }

    @Override
    @Cacheable(value = "councilSearchByWriter",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#writer, #pageable.pageNumber)")
    public Header<NoticeResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Council> councils = councilRepository.findAllByWriterContaining(writer, pageable);
        List<Council> councilsByStatus = searchByStatus();

        return response(councils, councilsByStatus);
    }

    @Override
    @Cacheable(value = "councilSearchByTitle",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #pageable.pageNumber)")
    public Header<NoticeResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Council> councils = councilRepository.findAllByTitleContaining(title, pageable);
        List<Council> councilsByStatus = searchByStatus();

        return response(councils, councilsByStatus);
    }

    @Override
    @Cacheable(value = "councilSearchByTitleOrContent",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #content, #pageable.pageNumber)")
    public Header<NoticeResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Council> councils = councilRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        List<Council> councilsByStatus = searchByStatus();

        return response(councils, councilsByStatus);
    }

    public List<Council> searchByStatus() {
        return councilRepository.findTop10ByStatusIn(Arrays.asList(BulletinStatus.URGENT, BulletinStatus.NOTICE));
    }

    private Header<NoticeResponseDTO> response(Page<Council> councils, List<Council> councilsByStatus) {
        NoticeResponseDTO noticeResponseDTO = NoticeResponseDTO.builder()
                .noticeApiResponseList(councils.stream()
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
        councilsByStatus.stream().forEach(notice -> {
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
            if (noticeApiResponse.getStatus() == BulletinStatus.NOTICE) {
                noticeApiNoticeResponseList.add(noticeApiResponse);
            } else if (noticeApiResponse.getStatus() == BulletinStatus.URGENT) {
                noticeApiUrgentResponseList.add(noticeApiResponse);
            }
        });
        noticeResponseDTO.setNoticeApiNoticeResponseList(noticeApiNoticeResponseList);
        noticeResponseDTO.setNoticeApiUrgentResponseList(noticeApiUrgentResponseList);

        Pagination pagination = Pagination.builder()
                .totalElements(councils.getTotalElements())
                .totalPages(councils.getTotalPages())
                .currentElements(councils.getNumberOfElements())
                .currentPage(councils.getNumber())
                .build();

        return Header.OK(noticeResponseDTO, pagination);
    }
}
