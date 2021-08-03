package com.aisw.community.service.user;

import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.user.UserApiRequest;
import com.aisw.community.model.network.request.user.VerificationApiRequest;
import com.aisw.community.model.network.response.user.UserApiResponse;
import com.aisw.community.model.network.response.user.VerificationApiResponse;
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

    public Header<VerificationApiResponse> verification(Header<VerificationApiRequest> request) {
        VerificationApiRequest verificationApiRequest = request.getData();
        User user = userRepository.findByUsername(verificationApiRequest.getUsername());
        VerificationApiResponse response = new VerificationApiResponse();
        if(user!=null) {
            response.setValidation(true);
        } else {
            response.setValidation(false);
            String email = verificationApiRequest.getEmail();
            email = email.replace(email.substring(0, email.indexOf('@') + 1), "");
            if(email.equals("gachon.ac.kr")) {
                response.setAccount("gachon");
            } else response.setAccount("general");
        }

        return Header.OK(response);
    }

    public Header<UserApiResponse> update(Header<UserApiRequest> request) {

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
