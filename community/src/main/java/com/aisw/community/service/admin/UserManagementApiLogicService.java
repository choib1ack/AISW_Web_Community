package com.aisw.community.service.admin;

import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.admin.UserManagementApiRequest;
import com.aisw.community.model.network.response.admin.UserManagementApiResponse;
import com.aisw.community.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManagementApiLogicService {

    @Autowired
    private UserRepository userRepository;

    public Header<List<UserManagementApiResponse>> readAll(Pageable pageable) {
        Page<User> userList = userRepository.findAll(pageable);

        List<UserManagementApiResponse> userManagementApiResponseList = userList.stream().map(user -> response(user)).collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(userList.getTotalElements())
                .totalPages(userList.getTotalPages())
                .currentElements(userList.getNumberOfElements())
                .currentPage(userList.getNumber())
                .build();

        return Header.OK(userManagementApiResponseList, pagination);
    }

    public Header<UserManagementApiResponse> changeRole(Header<UserManagementApiRequest> request) {
        UserManagementApiRequest userManagementApiRequest = request.getData();

        System.out.println(userManagementApiRequest);

        User user = userRepository.findById(userManagementApiRequest.getId())
                .orElseThrow(() -> new UserNotFoundException(userManagementApiRequest.getId()));

        user.setRole(userManagementApiRequest.getRole());
        User updateUser = userRepository.save(user);

        return Header.OK(response(updateUser));
    }

    private UserManagementApiResponse response(User user) {
        return UserManagementApiResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .studentId(user.getStudentId())
                .role(user.getRole())
                .build();
    }
}
