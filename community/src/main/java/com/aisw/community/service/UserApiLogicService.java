package com.aisw.community.service;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.UserApiRequest;
import com.aisw.community.model.network.response.UserApiResponse;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;


    // 1. request data
    // 2. user create
    // 3. created data -> UserApiResponse return
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        // 1. request data
        UserApiRequest userApiRequest = request.getData();

        // 2. user create
        User user = User.builder()
                .name(userApiRequest.getName())
                .email(userApiRequest.getEmail())
                .password(userApiRequest.getPassword())
                .phoneNumber(userApiRequest.getPhoneNumber())
                .grade(userApiRequest.getGrade())
                .studentId(userApiRequest.getStudentId())
                .createdAt(userApiRequest.getCreatedAt())
                .createdBy(userApiRequest.getCreatedBy())
                .updatedAt(userApiRequest.getUpdatedAt())
                .updatedBy(userApiRequest.getUpdatedBy())
                .level(userApiRequest.getLevel())
                .job(userApiRequest.getJob())
                .gender(userApiRequest.getGender())
                .university(userApiRequest.getUniversity())
                .college(userApiRequest.getCollege())
                .department(userApiRequest.getDepartment())
                .build();

        User newUser = userRepository.save(user);

        // 3. created user -> userApiResponse return : public Header<UserApiResponse> response(User user){}
        return response(newUser);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        // id -> repository getOne, getById
        Optional<User> optional = userRepository.findById(id);

        // user -> userApiResponse return
        return optional
                .map(user -> response(user))
                .orElseGet(
                        () -> Header.ERROR("No Data")
                );
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        // 1. data
        UserApiRequest userApiRequest = request.getData();

        // 2. id -> user data find
        Optional<User> optional = userRepository.findById(userApiRequest.getId());

        return optional.map(user ->{
            // 3. data -> update
            // id
            user.setName(userApiRequest.getName())
                    .setEmail(userApiRequest.getEmail())
                    .setPassword(userApiRequest.getPassword())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setGrade(userApiRequest.getGrade())
                    .setStudentId(userApiRequest.getStudentId())
                    .setCreatedAt(userApiRequest.getCreatedAt())
                    .setCreatedBy(userApiRequest.getCreatedBy())
                    .setUpdatedAt(userApiRequest.getUpdatedAt())
                    .setUpdatedBy(userApiRequest.getUpdatedBy())
                    .setLevel(userApiRequest.getLevel())
                    .setJob(userApiRequest.getJob())
                    .setGender(userApiRequest.getGender())
                    .setUniversity(userApiRequest.getUniversity())
                    .setCollege(userApiRequest.getCollege())
                    .setDepartment(userApiRequest.getDepartment());
            return user;
        }).map(user -> userRepository.save(user))
                .map(user -> response(user))
                .orElseGet(() -> Header.ERROR("No Data"));
    }

    @Override
    public Header delete(Long id) {
        // 1. id -> repository -> user
        Optional<User> optional = userRepository.findById(id);

        // 2. repository -> delete
        // 3. response return
        return optional.map(user -> {
            userRepository.delete(user);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("No Data"));
    }

    private Header<UserApiResponse> response(User user){
        // user -> userApiResponse return
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .grade(user.getGrade())
                .studentId(user.getStudentId())
//                .createdAt(user.getCreatedAt())
//                .createdBy(user.getCreatedBy())
//                .updatedAt(user.getUpdatedAt())
//                .updatedBy(user.getUpdatedBy())
                .level(user.getLevel())
                .job(user.getJob())
                .gender(user.getGender())
                .university(user.getUniversity())
                .college(user.getCollege())
                .department(user.getDepartment())
                .build();

        // Header + data
        return Header.OK(userApiResponse);
    }
}
