package com.aisw.community.service.post.notice;

import com.aisw.community.component.advice.exception.NotEqualUserException;
import com.aisw.community.component.advice.exception.PostNotFoundException;
import com.aisw.community.component.advice.exception.PostStatusNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.entity.post.notice.Department;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.post.notice.DepartmentApiRequest;
import com.aisw.community.model.network.request.post.notice.FileUploadToDepartmentRequest;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.model.network.response.post.notice.DepartmentApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.repository.post.file.FileRepository;
import com.aisw.community.repository.post.notice.DepartmentRepository;
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
public class DepartmentService extends NoticePostService<DepartmentApiRequest, FileUploadToDepartmentRequest, NoticeResponseDTO, DepartmentApiResponse, Department> {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "departmentReadAll", allEntries = true),
            @CacheEvict(value = "departmentSearchByWriter", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitle", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<DepartmentApiResponse> create(Authentication authentication, Header<DepartmentApiRequest> request) {
        DepartmentApiRequest departmentApiRequest = request.getData();
        if(departmentApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(departmentApiRequest.getStatus().getTitle());
        }

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        Department department = Department.builder()
                .title(departmentApiRequest.getTitle())
                .writer(user.getName())
                .content(departmentApiRequest.getContent())
                .status(departmentApiRequest.getStatus())
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.DEPARTMENT)
                .user(user)
                .build();

        Department newDepartment = baseRepository.save(department);
        return Header.OK(response(newDepartment));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "departmentReadAll", allEntries = true),
            @CacheEvict(value = "departmentSearchByWriter", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitle", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<DepartmentApiResponse> create(Authentication authentication, FileUploadToDepartmentRequest request) {
        DepartmentApiRequest departmentApiRequest = request.getDepartmentApiRequest();
        if(departmentApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(departmentApiRequest.getStatus().getTitle());
        }

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        Department department = Department.builder()
                .title(departmentApiRequest.getTitle())
                .writer(user.getName())
                .content(departmentApiRequest.getContent())
                .status(departmentApiRequest.getStatus())
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.DEPARTMENT)
                .user(user)
                .build();
        Department newDepartment = baseRepository.save(department);

        MultipartFile[] files = request.getFiles();
        List<FileApiResponse> fileApiResponseList = fileService.uploadFiles(files, newDepartment.getId(), UploadCategory.POST);

        return Header.OK(response(newDepartment, fileApiResponseList));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "departmentReadAll", allEntries = true),
            @CacheEvict(value = "departmentSearchByWriter", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitle", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true)
    })
    public Header<DepartmentApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(department -> department.setViews(department.getViews() + 1))
                .map(department -> baseRepository.save((Department)department))
                .map(this::response)
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "departmentReadAll", allEntries = true),
            @CacheEvict(value = "departmentSearchByWriter", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitle", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<DepartmentApiResponse> update(Authentication authentication, Header<DepartmentApiRequest> request) {
        DepartmentApiRequest departmentApiRequest = request.getData();
        if(departmentApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(departmentApiRequest.getStatus().getTitle());
        }

        Department department = baseRepository.findById(departmentApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(departmentApiRequest.getId()));

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if(department.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        department
                .setTitle(departmentApiRequest.getTitle())
                .setContent(departmentApiRequest.getContent())
                .setStatus(departmentApiRequest.getStatus());
        baseRepository.save(department);

        return Header.OK(response(department));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "departmentReadAll", allEntries = true),
            @CacheEvict(value = "departmentSearchByWriter", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitle", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<DepartmentApiResponse> update(Authentication authentication, FileUploadToDepartmentRequest request) {
        DepartmentApiRequest departmentApiRequest = request.getDepartmentApiRequest();
        if(departmentApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(departmentApiRequest.getStatus().getTitle());
        }

        Department department = baseRepository.findById(departmentApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(departmentApiRequest.getId()));

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if(department.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        MultipartFile[] files = request.getFiles();
        department.getFileList().stream().forEach(file -> fileRepository.delete(file));
        department.getFileList().clear();
        List<FileApiResponse> fileApiResponseList = fileService.uploadFiles(files, department.getId(), UploadCategory.POST);

        department
                .setTitle(departmentApiRequest.getTitle())
                .setContent(departmentApiRequest.getContent())
                .setStatus(departmentApiRequest.getStatus());
        baseRepository.save(department);

        return Header.OK(response(department, fileApiResponseList));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "departmentReadAll", allEntries = true),
            @CacheEvict(value = "departmentSearchByWriter", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitle", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitleOrContent", allEntries = true),
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
        Department department = baseRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if (department.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        baseRepository.delete(department);
        return Header.OK();
    }

    private DepartmentApiResponse response(Department department) {
        DepartmentApiResponse departmentApiResponse = DepartmentApiResponse.builder()
                .id(department.getId())
                .title(department.getTitle())
                .writer(department.getWriter())
                .content(department.getContent())
                .status(department.getStatus())
                .views(department.getViews())
                .category(department.getCategory())
                .createdAt(department.getCreatedAt())
                .createdBy(department.getCreatedBy())
                .updatedAt(department.getUpdatedAt())
                .updatedBy(department.getUpdatedBy())
                .build();
        if (department.getFileList() != null) {
            departmentApiResponse.setFileApiResponseList(fileService.getFileList(department.getFileList(), UploadCategory.POST, department.getId()));
        }

        return departmentApiResponse;
    }

    private DepartmentApiResponse response(Department department, List<FileApiResponse> fileApiResponseList) {
        DepartmentApiResponse departmentApiResponse = DepartmentApiResponse.builder()
                .id(department.getId())
                .title(department.getTitle())
                .writer(department.getWriter())
                .content(department.getContent())
                .status(department.getStatus())
                .views(department.getViews())
                .category(department.getCategory())
                .createdAt(department.getCreatedAt())
                .createdBy(department.getCreatedBy())
                .updatedAt(department.getUpdatedAt())
                .updatedBy(department.getUpdatedBy())
                .fileApiResponseList(fileApiResponseList)
                .build();

        return departmentApiResponse;
    }

    @Override
    @Cacheable(value = "departmentReadAll", key = "#pageable.pageNumber")
    public Header<NoticeResponseDTO> readAll(Pageable pageable) {
        Page<Department> departments = baseRepository.findAll(pageable);
        Page<Department> departmentsByStatus = searchByStatus(pageable);

        return getListHeader(departments, departmentsByStatus);
    }

    @Override
    @Cacheable(value = "departmentSearchByWriter",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#writer, #pageable.pageNumber)")
    public Header<NoticeResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Department> departments = departmentRepository.findAllByWriterContaining(writer, pageable);
        Page<Department> departmentsByStatus = searchByStatus(pageable);

        return getListHeader(departments, departmentsByStatus);
    }

    @Override
    @Cacheable(value = "departmentSearchByTitle",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #pageable.pageNumber)")
    public Header<NoticeResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Department> departments = departmentRepository.findAllByTitleContaining(title, pageable);
        Page<Department> departmentsByStatus = searchByStatus(pageable);

        return getListHeader(departments, departmentsByStatus);
    }

    @Override
    @Cacheable(value = "departmentSearchByTitleOrContent",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #content, #pageable.pageNumber)")
    public Header<NoticeResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Department> departments = departmentRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<Department> departmentsByStatus = searchByStatus(pageable);

        return getListHeader(departments, departmentsByStatus);
    }

    public Page<Department> searchByStatus(Pageable pageable) {
        Page<Department> departments = departmentRepository.findAllByStatusIn(
                Arrays.asList(BulletinStatus.URGENT, BulletinStatus.NOTICE), pageable);

        return departments;
    }

    private Header<NoticeResponseDTO> getListHeader
            (Page<Department> departments, Page<Department> departmentsByStatus) {
        NoticeResponseDTO noticeResponseDTO = NoticeResponseDTO.builder()
                .noticeApiResponseList(departments.stream()
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
        departmentsByStatus.stream().forEach(notice -> {
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
                .totalElements(departments.getTotalElements())
                .totalPages(departments.getTotalPages())
                .currentElements(departments.getNumberOfElements())
                .currentPage(departments.getNumber())
                .build();

        return Header.OK(noticeResponseDTO, pagination);
    }
}
