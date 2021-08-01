package com.aisw.community.model.network.request.user;

import com.aisw.community.model.enumclass.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountApiRequest {

    private String name;

    private String email;

    private String phoneNumber;

    private Grade grade;

    private Integer studentId;

    private Gender gender;

    private Campus university;

    private CollegeName collegeName;

    private DepartmentName departmentName;

    private UserRole role;
}
