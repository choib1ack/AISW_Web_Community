package com.aisw.community.service.user;

import com.aisw.community.component.advice.exception.PhoneNumberNotSuitableException;
import com.aisw.community.component.advice.exception.SignUpNotSuitableException;
import com.aisw.community.component.advice.exception.UserNotFoundException;
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

    public Header<UserApiResponse> signup(UserApiRequest userApiRequest) {
        if (userApiRequest.getProvider() == null || userApiRequest.getProviderId() == null)
            throw new SignUpNotSuitableException(userApiRequest.getProvider() + "_" + userApiRequest.getProviderId());
        if (!validatePhoneNumber(userApiRequest.getPhoneNumber()))
            throw new PhoneNumberNotSuitableException(userApiRequest.getPhoneNumber());

        User user = User.builder()
                .username(userApiRequest.getProvider() + "_" + userApiRequest.getProviderId())
                .name(userApiRequest.getName())
                .email(userApiRequest.getEmail())
                .password(bCryptPasswordEncoder.encode("AISW"))
                .phoneNumber(userApiRequest.getPhoneNumber())
                .grade(userApiRequest.getGrade())
                .gender(userApiRequest.getGender())
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

    public boolean isNumeric(String str) {
        return Pattern.matches("^[0-9]*$", str);
    }

    public Header<VerificationApiResponse> verification(VerificationRequest verificationRequest) {
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

    public Header<UserApiResponse> update(User user, UserApiRequest userApiRequest) {
        user
                .setName(userApiRequest.getName())
                .setEmail(userApiRequest.getEmail())
                .setPhoneNumber(userApiRequest.getPhoneNumber())
                .setGrade(userApiRequest.getGrade())
                .setGender(userApiRequest.getGender())
                .setDepartmentName(userApiRequest.getDepartmentName());
        User newUser = userRepository.save(user);

        return Header.OK(response(newUser));
    }

    public Header delete(User user) {
        userRepository.delete(user);
        return Header.OK();
    }

    public User getUser(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return principal.getUser();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private UserApiResponse response(User user) {
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .grade(user.getGrade())
                .gender(user.getGender())
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
