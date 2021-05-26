package com.aisw.community.service.user;

import com.aisw.community.advice.exception.AdminNotFoundException;
import com.aisw.community.advice.exception.PostNotFoundException;
import com.aisw.community.model.entity.post.notice.University;
import com.aisw.community.model.entity.user.AdminUser;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.notice.UniversityApiRequest;
import com.aisw.community.model.network.request.user.AdminUserApiRequest;
import com.aisw.community.model.network.response.user.AdminUserApiResponse;
import com.aisw.community.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminUserApiLogicService extends BaseService<AdminUserApiRequest, AdminUserApiResponse, AdminUser> {

    @Override
    public Header<AdminUserApiResponse> create(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest adminUserApiRequest = request.getData();

        AdminUser adminUser = AdminUser.builder()
                .name(adminUserApiRequest.getName())
                .email(adminUserApiRequest.getEmail())
                .password(adminUserApiRequest.getPassword())
                .phoneNumber(adminUserApiRequest.getPhoneNumber())
                .role(adminUserApiRequest.getRole())
                .build();

        AdminUser newAdminUser = baseRepository.save(adminUser);

        return Header.OK(response(newAdminUser));
    }

    @Override
    public Header<AdminUserApiResponse> read(Long id) {
        Optional<AdminUser> optional = baseRepository.findById(id);

        return optional.map(adminUser -> response(adminUser))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("No Data"));
    }

    @Override
    @Transactional
    public Header<AdminUserApiResponse> update(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest adminUserApiRequest = request.getData();

        AdminUser adminUser = baseRepository.findById(adminUserApiRequest.getId()).orElseThrow(
                () -> new AdminNotFoundException(adminUserApiRequest.getId()));

        adminUser.setName(adminUserApiRequest.getName())
                .setEmail(adminUserApiRequest.getEmail())
                .setPassword(adminUserApiRequest.getPassword())
                .setPhoneNumber(adminUserApiRequest.getPhoneNumber())
                .setRole(adminUserApiRequest.getRole());
        baseRepository.save(adminUser);

        return Header.OK(response(adminUser));
    }

    @Override
    public Header delete(Long id, Long userId) {
        AdminUser adminUser = baseRepository.findById(id).orElseThrow(() -> new AdminNotFoundException(id));
        baseRepository.delete(adminUser);
        return Header.OK();
    }

    public AdminUserApiResponse response(AdminUser adminUser){
        AdminUserApiResponse adminUserApiResponse = AdminUserApiResponse.builder()
                .name(adminUser.getName())
                .email(adminUser.getEmail())
                .password(adminUser.getPassword())
                .phoneNumber(adminUser.getPhoneNumber())
                .createdAt(adminUser.getCreatedAt())
                .createdBy(adminUser.getCreatedBy())
                .updatedAt(adminUser.getUpdatedAt())
                .updatedBy(adminUser.getUpdatedBy())
                .role(adminUser.getRole())
                .build();

        return adminUserApiResponse;
    }

    @Override
    public Header<List<AdminUserApiResponse>> search(Pageable pageable) {
        Page<AdminUser> adminUsers = baseRepository.findAll(pageable);

        List<AdminUserApiResponse> adminUserApiResponseList = adminUsers.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(adminUserApiResponseList);
    }
}
