package com.aisw.community.model.network.request.user;

import com.aisw.community.model.enumclass.Gender;
import com.aisw.community.model.enumclass.Grade;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserApiRequest {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;


    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "전화번호는 01x-xxxx-xxxx 형식이 필요합니다.")
    private String phoneNumber;

    private Grade grade;

    private Gender gender;

    private String university;

    private String departmentName;

    private String role;

    private String provider;

    private String providerId;
}
