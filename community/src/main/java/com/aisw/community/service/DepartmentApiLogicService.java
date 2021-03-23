package com.aisw.community.service;

import com.aisw.community.model.entity.Department;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.DepartmentApiRequest;
import com.aisw.community.model.network.response.DepartmentApiResponse;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.repository.DepartmentRepository;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentApiLogicService extends PostService<DepartmentApiRequest, NoticeApiResponse, DepartmentApiResponse, Department> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Header<DepartmentApiResponse> create(Header<DepartmentApiRequest> request) {
        DepartmentApiRequest departmentApiRequest = request.getData();

        Department department = Department.builder()
                .title(departmentApiRequest.getTitle())
                .writer(userRepository.getOne(departmentApiRequest.getUserId()).getName())
                .content(departmentApiRequest.getContent())
                .attachmentFile(departmentApiRequest.getAttachmentFile())
                .status(departmentApiRequest.getStatus())
                .views(0L)
                .level(departmentApiRequest.getLevel())
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.DEPARTMENT)
                .account(userRepository.getOne(departmentApiRequest.getUserId()))
                .build();

        Department newDepartment = baseRepository.save(department);
        return Header.OK(response(newDepartment));
    }

    @Override
    @Transactional
    public Header<DepartmentApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(department -> department.setViews(department.getViews() + 1))
                .map(department -> baseRepository.save((Department)department))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    @Transactional
    public Header<DepartmentApiResponse> update(Header<DepartmentApiRequest> request) {
        DepartmentApiRequest departmentApiRequest = request.getData();

        return baseRepository.findById(departmentApiRequest.getId())
                .map(department -> {
                    department
                            .setTitle(departmentApiRequest.getTitle())
                            .setContent(departmentApiRequest.getContent())
                            .setAttachmentFile(departmentApiRequest.getAttachmentFile())
                            .setStatus(departmentApiRequest.getStatus())
                            .setLevel(departmentApiRequest.getLevel());
                    return department;
                })
                .map(department -> baseRepository.save(department))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(department -> {
                    baseRepository.delete(department);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private DepartmentApiResponse response(Department department) {
        DepartmentApiResponse departmentApiResponse = DepartmentApiResponse.builder()
                .id(department.getId())
                .title(department.getTitle())
                .writer(department.getWriter())
                .content(department.getContent())
                .attachmentFile(department.getAttachmentFile())
                .status(department.getStatus())
                .views(department.getViews())
                .level(department.getLevel())
                .category(department.getCategory())
                .createdAt(department.getCreatedAt())
                .createdBy(department.getCreatedBy())
                .updatedAt(department.getUpdatedAt())
                .updatedBy(department.getUpdatedBy())
                .userId(department.getAccount().getId())
                .build();

        return departmentApiResponse;
    }

    @Override
    public Header<List<NoticeApiResponse>> search(Pageable pageable) {
        Page<Department> departments = baseRepository.findAll(pageable);

        return getListHeader(departments);
    }

    @Override
    public Header<List<NoticeApiResponse>> searchByWriter(String writer, Pageable pageable) {
        Page<Department> departments = departmentRepository.findAllByWriterContaining(writer, pageable);

        return getListHeader(departments);
    }

    @Override
    public Header<List<NoticeApiResponse>> searchByTitle(String title, Pageable pageable) {
        Page<Department> departments = departmentRepository.findAllByTitleContaining(title, pageable);

        return getListHeader(departments);
    }

    @Override
    public Header<List<NoticeApiResponse>> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Department> departments = departmentRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);

        return getListHeader(departments);
    }

    private Header<List<NoticeApiResponse>> getListHeader(Page<Department> departments) {
        List<NoticeApiResponse> noticeApiResponseList = departments.stream()
                .map(department -> NoticeApiResponse.builder()
                        .id(department.getId())
                        .title(department.getTitle())
                        .category(department.getCategory())
                        .createdAt(department.getCreatedAt())
                        .status(department.getStatus())
                        .views(department.getViews())
                        .writer(department.getWriter())
                        .build())
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(departments.getTotalElements())
                .totalPages(departments.getTotalPages())
                .currentElements(departments.getNumberOfElements())
                .currentPage(departments.getNumber())
                .build();

        return Header.OK(noticeApiResponseList, pagination);
    }
}
