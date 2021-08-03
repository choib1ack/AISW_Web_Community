package com.aisw.community.service.post.notice;

import com.aisw.community.advice.exception.NotEqualAccountException;
import com.aisw.community.advice.exception.PostNotFoundException;
import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.post.notice.Department;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.post.notice.DepartmentApiRequest;
import com.aisw.community.model.network.request.post.notice.FileUploadToDepartmentApiRequest;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.model.network.response.post.notice.DepartmentApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.repository.post.file.FileRepository;
import com.aisw.community.repository.post.notice.DepartmentRepository;
import com.aisw.community.repository.user.UserRepository;
import com.aisw.community.service.post.file.FileApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentApiLogicService extends NoticePostService<DepartmentApiRequest, FileUploadToDepartmentApiRequest, NoticeResponseDTO, DepartmentApiResponse, Department> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileApiLogicService fileApiLogicService;

    @Override
    public Header<DepartmentApiResponse> create(Header<DepartmentApiRequest> request) {
        DepartmentApiRequest departmentApiRequest = request.getData();
        User user = userRepository.findById(departmentApiRequest.getUserId()).orElseThrow(
                () -> new UserNotFoundException(departmentApiRequest.getUserId()));
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
    public Header<DepartmentApiResponse> create(FileUploadToDepartmentApiRequest request) {
        DepartmentApiRequest departmentApiRequest = request.getDepartmentApiRequest();

        User user = userRepository.findById(departmentApiRequest.getUserId()).orElseThrow(
                () -> new UserNotFoundException(departmentApiRequest.getUserId()));
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
        List<FileApiResponse> fileApiResponseList = fileApiLogicService.uploadFiles(files, newDepartment.getId(), UploadCategory.POST);

        return Header.OK(response(newDepartment, fileApiResponseList));
    }

    @Override
    @Transactional
    public Header<DepartmentApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(department -> department.setViews(department.getViews() + 1))
                .map(department -> baseRepository.save((Department)department))
                .map(this::response)
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Transactional
    public Header<DepartmentApiResponse> update(Header<DepartmentApiRequest> request) {
        DepartmentApiRequest departmentApiRequest = request.getData();

        Department department = baseRepository.findById(departmentApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(departmentApiRequest.getId()));


        if(department.getUser().getId() != departmentApiRequest.getUserId()) {
            throw new NotEqualAccountException(departmentApiRequest.getUserId());
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
    public Header<DepartmentApiResponse> update(FileUploadToDepartmentApiRequest request) {
        DepartmentApiRequest departmentApiRequest = request.getDepartmentApiRequest();
        MultipartFile[] files = request.getFiles();

        Department department = baseRepository.findById(departmentApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(departmentApiRequest.getId()));


        if(department.getUser().getId() != departmentApiRequest.getUserId()) {
            throw new NotEqualAccountException(departmentApiRequest.getUserId());
        }

        department.getFileList().stream().forEach(file -> fileRepository.delete(file));
        department.getFileList().clear();
        List<FileApiResponse> fileApiResponseList = fileApiLogicService.uploadFiles(files, department.getId(), UploadCategory.POST);

        department
                .setTitle(departmentApiRequest.getTitle())
                .setContent(departmentApiRequest.getContent())
                .setStatus(departmentApiRequest.getStatus());
        baseRepository.save(department);

        return Header.OK(response(department, fileApiResponseList));
    }

    @Override
    public Header delete(Long id, Long userId) {
        Department department = baseRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        if (department.getUser().getId() != userId) {
            throw new NotEqualAccountException(userId);
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
                .accountId(department.getUser().getId())
                .fileApiResponseList(department.getFileList().stream()
                        .map(file -> fileApiLogicService.response(file)).collect(Collectors.toList()))
                .build();

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
                .accountId(department.getUser().getId())
                .fileApiResponseList(fileApiResponseList)
                .build();

        return departmentApiResponse;
    }

    @Override
    @Cacheable(value = "departmentSearch", key = "#pageable.pageNumber")

    public Header<NoticeResponseDTO> search(Pageable pageable) {
        Page<Department> departments = baseRepository.findAll(pageable);
        Page<Department> departmentsByStatus = searchByStatus(pageable);

        return getListHeader(departments, departmentsByStatus);
    }

    @Override
    public Header<NoticeResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Department> departments = departmentRepository.findAllByWriterContaining(writer, pageable);
        Page<Department> departmentsByStatus = searchByStatus(pageable);

        return getListHeader(departments, departmentsByStatus);
    }

    @Override
    public Header<NoticeResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Department> departments = departmentRepository.findAllByTitleContaining(title, pageable);
        Page<Department> departmentsByStatus = searchByStatus(pageable);

        return getListHeader(departments, departmentsByStatus);
    }

    @Override
    public Header<NoticeResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Department> departments = departmentRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<Department> departmentsByStatus = searchByStatus(pageable);

        return getListHeader(departments, departmentsByStatus);
    }

    public Page<Department> searchByStatus(Pageable pageable) {
        Page<Department> departments = departmentRepository.findAllByStatusOrStatus(
                BulletinStatus.URGENT, BulletinStatus.NOTICE, pageable);

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
