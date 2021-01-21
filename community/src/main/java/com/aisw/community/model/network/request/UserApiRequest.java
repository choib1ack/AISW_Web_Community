package com.aisw.community.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserApiRequest {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private Integer grade;

    private Integer studentId;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private Integer level;

    private String job;

    private Integer gender;

    private String university;

    private String college;

    private String department;
}
