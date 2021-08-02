package com.aisw.community.service.user;

import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.user.SignupApiRequest;
import com.aisw.community.model.network.request.user.UserApiRequest;
import com.aisw.community.model.network.request.user.VerificationApiRequest;
import com.aisw.community.model.network.response.user.UserApiResponse;
import com.aisw.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserApiService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    public Header<UserApiResponse> signup(Header<UserApiRequest> request) {
        UserApiRequest userApiRequest = request.getData();
        System.out.println(userApiRequest.getProvider());
        System.out.println(userApiRequest.getProviderId());

        if (userApiRequest.getProvider() != null && userApiRequest.getProviderId() != null) {
            User user = User.builder()
                    .username(userApiRequest.getProvider() + "_" + userApiRequest.getProviderId())
                    .name(userApiRequest.getName())
                    .email(userApiRequest.getEmail())
                    .password(bCryptPasswordEncoder.encode("AISW"))
                    .phoneNumber(userApiRequest.getPhoneNumber())
                    .grade(userApiRequest.getGrade())
                    .studentId(userApiRequest.getStudentId())
                    .gender(userApiRequest.getGender())
                    .university(userApiRequest.getUniversity())
                    .collegeName(userApiRequest.getCollegeName())
                    .departmentName(userApiRequest.getDepartmentName())
                    .role(userApiRequest.getRole())
                    .build();

            User newUser = userRepository.save(user);
            return Header.OK(response(newUser));
        }
        return Header.ERROR("request is wrong");
    }

    public String verification(Header<SignupApiRequest> request) {
        SignupApiRequest signupApiRequest = request.getData();
        User user = userRepository.findByUsername(signupApiRequest.getUsername());
        if(user!=null) return "MEMBER";

        String email = signupApiRequest.getEmail();
        email = email.replace(email.substring(0, email.indexOf('@') + 1), "");
        if(email.equals("gachon.ac.kr")) return "GACHON";

        return "GENERAL";
    }

    private UserApiResponse response(User user) {
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .grade(user.getGrade())
                .studentId(user.getStudentId())
                .gender(user.getGender())
                .university(user.getUniversity())
                .collegeName(user.getCollegeName())
                .departmentName(user.getDepartmentName())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .createdBy(user.getCreatedBy())
                .updatedAt(user.getUpdatedAt())
                .updatedBy(user.getUpdatedBy())
                .build();

        return userApiResponse;
    }
}
