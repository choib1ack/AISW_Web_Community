package com.aisw.community.service;

import com.aisw.community.controller.CrudController;
import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.Council;
import com.aisw.community.model.entity.Department;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.DepartmentApiRequest;
import com.aisw.community.model.network.request.NoticeApiRequest;
import com.aisw.community.model.network.response.CouncilApiResponse;
import com.aisw.community.model.network.response.DepartmentApiResponse;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.repository.DepartmentRepository;
import com.aisw.community.repository.NoticeRepository;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentApiLogicService extends BaseService<DepartmentApiRequest, DepartmentApiResponse, Department> {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoticeApiLogicService noticeApiLogicService;

    @Override
    public Header<DepartmentApiResponse> create(Header<DepartmentApiRequest> request) {
        DepartmentApiRequest departmentApiRequest = request.getData();

        NoticeApiRequest noticeApiRequest = NoticeApiRequest.builder().build();
        NoticeApiResponse noticeApiResponse = noticeApiLogicService.create(Header.OK(noticeApiRequest)).getData();

        Department department = Department.builder()
                .title(departmentApiRequest.getTitle())
                .content(departmentApiRequest.getContent())
                .attachmentFile(departmentApiRequest.getAttachmentFile())
                .status(departmentApiRequest.getStatus())
                .views(departmentApiRequest.getViews())
                .level(departmentApiRequest.getLevel())
                .user(userRepository.getOne(departmentApiRequest.getUserId()))
                .notice(noticeRepository.getOne(noticeApiResponse.getId()))
                .build();

        Department newDepartment = baseRepository.save(department);
        return Header.OK(response(newDepartment));
    }

    @Override
    public Header<DepartmentApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<DepartmentApiResponse> update(Header<DepartmentApiRequest> request) {
        DepartmentApiRequest departmentApiRequest = request.getData();

        return baseRepository.findById(departmentApiRequest.getId())
                .map(department -> {
                    department
                            .setTitle(departmentApiRequest.getTitle())
                            .setContent(departmentApiRequest.getContent())
                            .setAttachmentFile(departmentApiRequest.getAttachmentFile())
                            .setStatus(departmentApiRequest.getStatus())
                            .setViews(departmentApiRequest.getViews())
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
                    noticeRepository.findById(department.getNotice().getId())
                            .map(notice -> {
                                noticeRepository.delete(notice);
                                return Header.OK();
                            })
                            .orElseGet(() -> Header.ERROR("데이터 없음"));
                    baseRepository.delete(department);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private DepartmentApiResponse response(Department department) {
        DepartmentApiResponse departmentApiResponse = DepartmentApiResponse.builder()
                .id(department.getId())
                .title(department.getTitle())
                .content(department.getContent())
                .attachmentFile(department.getAttachmentFile())
                .status(department.getStatus())
                .createdAt(department.getCreatedAt())
                .createdBy(department.getCreatedBy())
                .updatedAt(department.getUpdatedAt())
                .updatedBy(department.getUpdatedBy())
                .views(department.getViews())
                .level(department.getLevel())
                .userId(department.getUser().getId())
                .noticeId(department.getNotice().getId())
                .build();

        return departmentApiResponse;
    }

    @Override
    public Header<List<DepartmentApiResponse>> search(Pageable pageable) {
        Page<Department> departments = baseRepository.findAll(pageable);

        List<DepartmentApiResponse> departmentApiResponseList = departments.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(departmentApiResponseList);
    }
}
