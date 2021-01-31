package com.aisw.community.service;

import com.aisw.community.model.entity.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.UserApiRequest;
import com.aisw.community.model.network.response.UserApiResponse;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {

    @Autowired
    private UserRepository userRepository;

    // 1. request data
    // 2. user create
    // 3. created data -> UserApiResponse return
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        // 1. request data
        UserApiRequest userApiRequest = request.getData();

        System.out.println(userApiRequest);
        // 2. user create
        User user = User.builder()
                .name(userApiRequest.getName())
                .email(userApiRequest.getEmail())
                .password(userApiRequest.getPassword())
                .phoneNumber(userApiRequest.getPhoneNumber())
                .grade(userApiRequest.getGrade())
                .studentId(userApiRequest.getStudentId())
                .level(userApiRequest.getLevel())
                .job(userApiRequest.getJob())
                .gender(userApiRequest.getGender())
                .university(userApiRequest.getUniversity())
                .college(userApiRequest.getCollege())
                .department(userApiRequest.getDepartment())
                .role(userApiRequest.getRole())
                .build();


        User newUser = baseRepository.save(user);

        // 3. created user -> userApiResponse return : public Header<UserApiResponse> response(User user){}
        return Header.OK(response(newUser));
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        // id -> repository getOne, getById
        Optional<User> optional = baseRepository.findById(id);

        // user -> userApiResponse return
        return optional
                .map(user -> response(user))
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("No Data")
                );
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        // 1. data
        UserApiRequest userApiRequest = request.getData();

        // 2. id -> user data find
        Optional<User> optional = baseRepository.findById(userApiRequest.getId());

        return optional.map(user ->{
            // 3. data -> update
            // id
            user.setName(userApiRequest.getName())
                    .setEmail(userApiRequest.getEmail())
                    .setPassword(userApiRequest.getPassword())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setGrade(userApiRequest.getGrade())
                    .setStudentId(userApiRequest.getStudentId())
                    .setLevel(userApiRequest.getLevel())
                    .setJob(userApiRequest.getJob())
                    .setGender(userApiRequest.getGender())
                    .setUniversity(userApiRequest.getUniversity())
                    .setCollege(userApiRequest.getCollege())
                    .setDepartment(userApiRequest.getDepartment());
            return user;
        }).map(user -> baseRepository.save(user))
                .map(user -> response(user))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("No Data"));
    }

    @Override
    public Header delete(Long id) {
        // 1. id -> repository -> user
        Optional<User> optional = baseRepository.findById(id);

        // 2. repository -> delete
        // 3. response return
        return optional.map(user -> {
            baseRepository.delete(user);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("No Data"));
    }

    private UserApiResponse response(User user){
        // user -> userApiResponse return
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .grade(user.getGrade())
                .studentId(user.getStudentId())
                .createdAt(user.getCreatedAt())
                .createdBy(user.getCreatedBy())
                .updatedAt(user.getUpdatedAt())
                .updatedBy(user.getUpdatedBy())
                .level(user.getLevel())
                .job(user.getJob())
                .gender(user.getGender())
                .university(user.getUniversity())
                .college(user.getCollege())
                .department(user.getDepartment())
                .role(user.getRole())
                .build();

        // Header + data
        return userApiResponse;
    }

    @Override
    public Header<List<UserApiResponse>> search(Pageable pageable) {
        return null;
    }

//    // Login Api
//    public Header<UserApiResponse> login(Header<UserApiRequest> request){
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        UserApiRequest userApiRequest = request.getData();
//        String inputEmail = userApiRequest.getEmail();
//        String inputPw = encoder.encode(userApiRequest.getPassword());
//
//        System.out.println(inputPw);
//        Optional<User> optional = userRepository.findByEmail(inputEmail);
//
//        // 1. Check input email exists in database
//        // If exists, input email's password is same with password in database
//        if(optional.isPresent()) {
//            // If same, return OK
//            return optional.map(user -> passwordCheck(user, inputPw))
//                    .map(Header::OK)
//                    .orElseGet(() -> Header.ERROR("Wrong Password"));
//                    // If not same, return error with description("Wrong password")
//        }
//        // If not exists, return error with description("Email Not Exists")
//        else{
//            return Header.ERROR("Email Not Exists");
//        }
//    }
//
//    //Check input email's password is same with password in database
//    // 1. If same, send User Info to http
//    // 2. If not same, return null
//    private UserApiResponse passwordCheck(User user, String pw){
//        if(user.getPassword() == pw)
//            return response(user);
//        else
//            return null;
//    }
//
//    @Override
//    public Header<List<UserApiResponse>> search(Pageable pageable) {
//        Page<User> users = baseRepository.findAll(pageable);
//
//        List<UserApiResponse> userApiResponseList = users.stream()
//                .map(this::response)
//                .collect(Collectors.toList());
//
//        return Header.OK(userApiResponseList);
//    }
}
