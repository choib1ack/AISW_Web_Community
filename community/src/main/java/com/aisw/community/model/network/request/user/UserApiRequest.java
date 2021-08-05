package com.aisw.community.model.network.request.user;

import com.aisw.community.model.enumclass.Gender;
import com.aisw.community.model.enumclass.Grade;
import lombok.*;

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

    private String studentId;

    private Gender gender;

    private String university;

    private String collegeName;

    private String departmentName;

    private String role;

    private String provider;

    private String providerId;
}
