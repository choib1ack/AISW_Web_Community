package com.aisw.community.service.user;

import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
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

        // Token 보내기
    }

    public String verification(Header<VerificationApiRequest> request) {
        VerificationApiRequest verificationApiRequest = request.getData();
        // 이미 가입 된 유저
        User user = userRepository.findByUsername(verificationApiRequest.getUsername());
        if (user != null) return "MEMBER";

        // 가입해야 되는 유저
        // 가천대학교 계정으로 로그인 하는 경우
        String email = verificationApiRequest.getEmail()
                .replace(verificationApiRequest.getEmail().substring(0, verificationApiRequest.getEmail().indexOf("@") + 1), "");
        if(email.equals("gachon.ac.kr")) return "GACHON";

        // 구글 계정으로 로그인 하는 경우
        return "GOOGLE";
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
