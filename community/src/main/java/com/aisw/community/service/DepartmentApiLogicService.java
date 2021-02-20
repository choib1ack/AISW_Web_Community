package com.aisw.community.service;

import com.aisw.community.model.entity.Department;
import com.aisw.community.model.enumclass.NoticeCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.DepartmentApiRequest;
import com.aisw.community.model.network.response.DepartmentApiResponse;
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
public class DepartmentApiLogicService extends PostService<DepartmentApiRequest, DepartmentApiResponse, Department> {

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
                .views(departmentApiRequest.getViews())
                .level(departmentApiRequest.getLevel())
                .category(NoticeCategory.DEPARTMENT)
                .user(userRepository.getOne(departmentApiRequest.getUserId()))
                .build();

        Department newDepartment = baseRepository.save(department);
        return Header.OK(response(newDepartment));
    }

    @Override
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
                .userId(department.getUser().getId())
                .build();

        return departmentApiResponse;
    }

    @Override
    public Header<List<DepartmentApiResponse>> search(Pageable pageable) {
        Page<Department> departments = baseRepository.findAll(pageable);

        return getListHeader(departments);
    }

    @Override
    public Header<List<DepartmentApiResponse>> searchByWriter(String writer, Pageable pageable) {
        Page<Department> departments = departmentRepository.findAllByWriterContaining(writer, pageable);

        return getListHeader(departments);
    }

    @Override
    public Header<List<DepartmentApiResponse>> searchByTitle(String title, Pageable pageable) {
        Page<Department> departments = departmentRepository.findAllByTitleContaining(title, pageable);

        return getListHeader(departments);
    }

    @Override
    public Header<List<DepartmentApiResponse>> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Department> departments = departmentRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);

        return getListHeader(departments);
    }

    private Header<List<DepartmentApiResponse>> getListHeader(Page<Department> departments) {
        List<DepartmentApiResponse> departmentApiResponseList = departments.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(departments.getTotalElements())
                .totalPages(departments.getTotalPages())
                .currentElements(departments.getNumberOfElements())
                .currentPage(departments.getNumber())
                .build();

        return Header.OK(departmentApiResponseList, pagination);
    }
}
