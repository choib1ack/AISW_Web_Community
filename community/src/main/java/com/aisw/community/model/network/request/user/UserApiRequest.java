package com.aisw.community.model.network.request.user;

import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.Gender;
import com.aisw.community.model.enumclass.Grade;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserApiRequest {

    private String name;

    private String email;

    private String phoneNumber;

    private Grade grade;

    private Gender gender;

    private String university;

    private String departmentName;

    private String role;

    private String provider;

    private String providerId;

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return User.builder()
                .username(provider + "_" + providerId)
                .name(name)
                .email(email)
                .password(bCryptPasswordEncoder.encode("AISW"))
                .phoneNumber(phoneNumber)
                .grade(grade)
                .gender(gender)
                .departmentName(departmentName)
                .role(role).build();
    }
}
