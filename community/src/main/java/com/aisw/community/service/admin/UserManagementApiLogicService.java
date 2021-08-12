package com.aisw.community.service.admin;

import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.admin.UserManagementApiRequest;
import com.aisw.community.model.network.response.admin.UserManagementApiResponse;
import com.aisw.community.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManagementApiLogicService {

    @Autowired
    private UserRepository userRepository;

    @Cacheable(value = "userManagementReadAll", key = "#pageable.pageNumber")
    public Header<List<UserManagementApiResponse>> readAll(Pageable pageable) {
        Page<User> userList = userRepository.findAll(pageable);

        List<UserManagementApiResponse> userManagementApiResponseList = userList.stream()
                .map(user -> response(user)).collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(userList.getTotalElements())
                .totalPages(userList.getTotalPages())
                .currentElements(userList.getNumberOfElements())
                .currentPage(userList.getNumber())
                .build();

        return Header.OK(userManagementApiResponseList, pagination);
    }

    @CacheEvict(value = "userManagementReadAll", allEntries = true)
    public Header<UserManagementApiResponse> changeRole(Header<UserManagementApiRequest> request) {
        UserManagementApiRequest userManagementApiRequest = request.getData();

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
