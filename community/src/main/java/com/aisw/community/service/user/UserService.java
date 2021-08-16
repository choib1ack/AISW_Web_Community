package com.aisw.community.service.user;

import com.aisw.community.component.advice.exception.PhoneNumberNotSuitableException;
import com.aisw.community.component.advice.exception.SignUpNotSuitableException;
import com.aisw.community.component.advice.exception.StudentIdNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.user.UserApiRequest;
import com.aisw.community.model.network.request.user.VerificationRequest;
import com.aisw.community.model.network.response.user.UserApiResponse;
import com.aisw.community.model.network.response.user.VerificationApiResponse;
import com.aisw.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    public Header<UserApiResponse> signup(Header<UserApiRequest> request) {
        UserApiRequest userApiRequest = request.getData();

        if (userApiRequest.getProvider() == null || userApiRequest.getProviderId() == null)
            throw new SignUpNotSuitableException(userApiRequest.getProvider() + "_" + userApiRequest.getProviderId());
        if (!validatePhoneNumber(userApiRequest.getPhoneNumber()))
            throw new PhoneNumberNotSuitableException(userApiRequest.getPhoneNumber());
        if (!validateStudentId(userApiRequest.getStudentId()))
            throw new StudentIdNotSuitableException(userApiRequest.getStudentId());

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

    private boolean validatePhoneNumber(String phoneNumber) {
        // 숫자 & 11자리 & 앞 3글자 010
        return isNumeric(phoneNumber) && (phoneNumber.length() == 11) && phoneNumber.substring(0, 3).equals("010");
    }

    private boolean validateStudentId(String studentId) {
        // 숫자 & 9자리
        return isNumeric(studentId) && (studentId.length() == 9);
    }

    public boolean isNumeric(String str) {
        return Pattern.matches("^[0-9]*$", str);
    }

    public Header<VerificationApiResponse> verification(Header<VerificationRequest> request) {
        VerificationRequest verificationRequest = request.getData();
        User user = userRepository.findByUsername(verificationRequest.getUsername());
        VerificationApiResponse response = new VerificationApiResponse();
        if (user != null) {
            response.setValidation(true);
        } else {
            response.setValidation(false);
            String email = verificationRequest.getEmail();
            email = email.replace(email.substring(0, email.indexOf('@') + 1), "");
            if (email.equals("gachon.ac.kr")) {
                response.setAccount("gachon");
            } else response.setAccount("general");
        }

        return Header.OK(response);
    }

    public Header<UserApiResponse> update(Authentication authentication, Header<UserApiRequest> request) {
        UserApiRequest userApiRequest = request.getData();
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        user
                .setName(userApiRequest.getName())
                .setEmail(userApiRequest.getEmail())
                .setPhoneNumber(userApiRequest.getPhoneNumber())
                .setGrade(userApiRequest.getGrade())
                .setStudentId(userApiRequest.getStudentId())
                .setGender(userApiRequest.getGender())
                .setUniversity(userApiRequest.getUniversity())
                .setCollegeName(userApiRequest.getCollegeName())
                .setDepartmentName(userApiRequest.getDepartmentName());
        User newUser = userRepository.save(user);

        return Header.OK(response(newUser));
    }

    public Header delete(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        userRepository.delete(user);
        return Header.OK();
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