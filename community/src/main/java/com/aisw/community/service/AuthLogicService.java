package com.aisw.community.service;

import com.aisw.community.ifs.AuthService;
import com.aisw.community.model.LoginParam;
import com.aisw.community.model.entity.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.LoginApiRequest;
import com.aisw.community.model.network.request.UserApiRequest;
import com.aisw.community.model.network.response.LoginApiResponse;
import com.aisw.community.model.network.response.UserApiResponse;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthLogicService implements AuthService {

    @Autowired
    private UserRepository userRepository;

//    private PasswordEncoder encoder;

    @Override
    public Header<UserApiResponse> signUpUser(Header<UserApiRequest> request){
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
                .level(userApiRequest.getLevel())
                .job(userApiRequest.getJob())
                .gender(userApiRequest.getGender())
                .university(userApiRequest.getUniversity())
                .college(userApiRequest.getCollege())
                .department(userApiRequest.getDepartment())
                .role(userApiRequest.getRole())
                .build();

        User newUser = userRepository.save(user);

        return Header.OK(response(newUser));
    }

    @Override
    public Header<UserApiResponse> loginUser(Header<LoginParam> request) {
        String email = request.getData().getEmail();
        String password = request.getData().getPassword();
//        password = encoder.encode(loginParam.getPassword());
        Optional<User> optional = userRepository.findByEmail(email);

        if(optional.isPresent()){
            return optional.map(user -> loginCheck(user, password))
                    .map(Header::OK)
                    .orElseGet(() -> Header.ERROR("Wrong Password"));
        }
        else
            return Header.ERROR("Email Not Exists");
    }

    public UserApiResponse loginCheck(User user, String password){
        String pw = user.getPassword();
//        pw = encoder.encode(pw);
        if(pw.equals(password))
            return response(user);
        else
            return null;
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

}
