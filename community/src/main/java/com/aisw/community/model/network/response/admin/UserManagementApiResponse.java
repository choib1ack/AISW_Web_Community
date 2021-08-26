package com.aisw.community.model.network.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementApiResponse {

    private Long id;

    private String name;

    private String email;

    private String role;
}
