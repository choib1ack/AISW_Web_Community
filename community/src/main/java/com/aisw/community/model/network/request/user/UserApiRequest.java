package com.aisw.community.model.network.request.user;

import com.aisw.community.model.enumclass.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserApiRequest {

    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private Grade grade;

    private Integer studentId;

    private Gender gender;

    private String university;

    private String collegeName;

    private String departmentName;

    private String role;

    private String provider;

    private String providerId;
}
