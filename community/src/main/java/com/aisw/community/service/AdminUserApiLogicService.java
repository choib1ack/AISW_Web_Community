package com.aisw.community.service;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.AdminUser;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.AdminUserApiRequest;
import com.aisw.community.model.network.response.AdminUserApiResponse;
import com.aisw.community.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        return response(newAdminUser);
    }

    @Override
    public Header<AdminUserApiResponse> read(Long id) {
        Optional<AdminUser> optional = baseRepository.findById(id);

        return optional.map(adminUser -> response(adminUser))
                .orElseGet(() -> Header.ERROR("No Data"));
    }

    @Override
    public Header<AdminUserApiResponse> update(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest adminUserApiRequest = request.getData();

        Optional<AdminUser> optional = baseRepository.findById(adminUserApiRequest.getId());

        return optional.map(adminUser -> {

            adminUser.setName(adminUserApiRequest.getName())
                    .setEmail(adminUserApiRequest.getEmail())
                    .setPassword(adminUserApiRequest.getPassword())
                    .setPhoneNumber(adminUserApiRequest.getPhoneNumber())
                    .setRole(adminUserApiRequest.getRole());

            return adminUser;
        }).map(adminUser -> baseRepository.save(adminUser))
                .map(adminUser -> response(adminUser))
                .orElseGet(() -> Header.ERROR("No Data"));
    }

    @Override
    public Header delete(Long id) {
        Optional<AdminUser> optional = baseRepository.findById(id);

        return optional.map(adminUser -> {
            baseRepository.delete(adminUser);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("No Data"));
    }

    public Header<AdminUserApiResponse> response(AdminUser adminUser){
        AdminUserApiResponse adminUserApiResponse = AdminUserApiResponse.builder()
                .name(adminUser.getName())
                .email(adminUser.getEmail())
                .password(adminUser.getPassword())
                .phoneNumber(adminUser.getPhoneNumber())
                .role(adminUser.getRole())
                .build();

        return Header.OK(adminUserApiResponse);
    }
}
