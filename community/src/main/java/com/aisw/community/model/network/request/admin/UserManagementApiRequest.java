package com.aisw.community.model.network.request.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserManagementApiRequest {

    private Long id;

    private String name;

    private Integer studentId;

    private String role;
}