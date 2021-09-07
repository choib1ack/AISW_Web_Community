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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    @CacheEvict(value = "userManagementReadAll", allEntries = true)
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

    @CacheEvict(value = "userManagementReadAll", allEntries = true)
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

    @Caching(evict = {
            @CacheEvict(value = "bannerRead", allEntries = true),
            @CacheEvict(value = "siteRead", allEntries = true),
            @CacheEvict(value = "userManagementReadAll", allEntries = true),
            @CacheEvict(value = "freeReadAll", allEntries = true),
            @CacheEvict(value = "freeSearchByWriter", allEntries = true),
            @CacheEvict(value = "freeSearchByTitle", allEntries = true),
            @CacheEvict(value = "freeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "jobReadAll", allEntries = true),
            @CacheEvict(value = "jobSearchByWriter", allEntries = true),
            @CacheEvict(value = "jobSearchByTitle", allEntries = true),
            @CacheEvict(value = "jobSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "qnaReadAll", allEntries = true),
            @CacheEvict(value = "qnaSearchByWriter", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitle", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "councilReadAll", allEntries = true),
            @CacheEvict(value = "councilSearchByWriter", allEntries = true),
            @CacheEvict(value = "councilSearchByTitle", allEntries = true),
            @CacheEvict(value = "councilSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "departmentReadAll", allEntries = true),
            @CacheEvict(value = "departmentSearchByWriter", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitle", allEntries = true),
            @CacheEvict(value = "departmentSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "universityReadAll", allEntries = true),
            @CacheEvict(value = "universitySearchByWriter", allEntries = true),
            @CacheEvict(value = "universitySearchByTitle", allEntries = true),
            @CacheEvict(value = "universitySearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "noticeReadAll", allEntries = true),
            @CacheEvict(value = "noticeSearchByWriter", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitle", allEntries = true),
            @CacheEvict(value = "noticeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
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
