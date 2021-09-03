package com.aisw.community.service.post.notice;

import com.aisw.community.component.advice.exception.NotEqualUserException;
import com.aisw.community.component.advice.exception.PostNotFoundException;
import com.aisw.community.component.advice.exception.PostStatusNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.entity.post.file.File;
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
import com.aisw.community.service.user.UserService;
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
public class DepartmentService implements NoticePostService<DepartmentApiRequest, DepartmentApiResponse, NoticeResponseDTO> {

    @Autowired
    private DepartmentRepository departmentRepository;

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
    public Header<DepartmentApiResponse> create(User user, DepartmentApiRequest departmentApiRequest) {
        Department department = Department.builder()
                .title(departmentApiRequest.getTitle())
                .writer(user.getName())
                .content(departmentApiRequest.getContent())
                .status(departmentApiRequest.getStatus())
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.DEPARTMENT)
                .user(user)
                .build();

        Department newDepartment = departmentRepository.save(department);
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
    public Header<DepartmentApiResponse> create(User user, DepartmentApiRequest departmentApiRequest, MultipartFile[] files) {
        Department department = Department.builder()
                .title(departmentApiRequest.getTitle())
                .writer(user.getName())
                .content(departmentApiRequest.getContent())
                .status(departmentApiRequest.getStatus())
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.DEPARTMENT)
                .user(user)
                .build();
        Department newDepartment = departmentRepository.save(department);

        if(files != null) {
            List<FileApiResponse> fileApiResponseList =
                    fileService.uploadFiles(files, user.getUsername(), "/auth-student/notice/department", newDepartment.getId(), UploadCategory.POST);

            return Header.OK(response(newDepartment, fileApiResponseList));
        } else {
            return Header.OK(response(newDepartment));
        }
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
    public Header<DepartmentApiResponse> read(User user, Long id) {
        return departmentRepository.findById(id)
                .map(department -> department.setViews(department.getViews() + 1))
                .map(department -> departmentRepository.save((Department)department))
                .map(department -> response(user, department))
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
    public Header<DepartmentApiResponse> update(User user, DepartmentApiRequest departmentApiRequest) {
        Department department = departmentRepository.findById(departmentApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(departmentApiRequest.getId()));
        if(department.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        department
                .setTitle(departmentApiRequest.getTitle())
                .setContent(departmentApiRequest.getContent())
                .setStatus(departmentApiRequest.getStatus());
        departmentRepository.save(department);

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
    public Header<DepartmentApiResponse> update(User user, DepartmentApiRequest departmentApiRequest, MultipartFile[] files, List<Long> delFileIdList) {
        Department department = departmentRepository.findById(departmentApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(departmentApiRequest.getId()));
        if(department.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        department
                .setTitle(departmentApiRequest.getTitle())
                .setContent(departmentApiRequest.getContent())
                .setStatus(departmentApiRequest.getStatus());
        departmentRepository.save(department);

        if(department.getFileList() != null && delFileIdList != null) {
            List<File> delFileList = new ArrayList<>();
            for(File file : department.getFileList()) {
                if(delFileIdList.contains(file.getId())) {
                    fileService.deleteFile(file);
                    delFileList.add(file);
                }
            }
            for (File file : delFileList) {
                department.getFileList().remove(file);
            }
        }
        if(files != null) {
            List<FileApiResponse> fileApiResponseList =
                    fileService.uploadFiles(files, user.getUsername(), "/auth-student/notice/department", department.getId(), UploadCategory.POST);
            return Header.OK(response(department, fileApiResponseList));
        } else {
            return Header.OK(response(department));
        }
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
    public Header delete(User user, Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        if (department.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        fileService.deleteFileList(department.getFileList());
        departmentRepository.delete(department);
        return Header.OK();
    }

    private DepartmentApiResponse response(Department department) {
        return DepartmentApiResponse.builder()
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
    }

    private DepartmentApiResponse response(User user, Department department) {
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
                .isWriter((user.getId() == department.getUser().getId()) ? true : false)
                .build();
        if (department.getFileList() != null) {
            departmentApiResponse.setFileApiResponseList(fileService.getFileList(department.getFileList()));
        }

        return departmentApiResponse;
    }

    private DepartmentApiResponse response(Department department, List<FileApiResponse> fileApiResponseList) {
        return DepartmentApiResponse.builder()
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
    }

    @Override
    @Cacheable(value = "departmentReadAll", key = "#pageable.pageNumber")
    public Header<NoticeResponseDTO> readAll(Pageable pageable) {
        Page<Department> departments = departmentRepository.findAll(pageable);
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
