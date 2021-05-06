package com.aisw.community.model.network.response.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginApiResponse {
    private String email;

    private String password;
}
