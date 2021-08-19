package com.aisw.community.model.network.response.admin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserManagementApiResponse {

    private Long id;

    private String name;

    private String email;

    private String role;
}
