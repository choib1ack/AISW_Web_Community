package com.aisw.community.model.network.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginApiResponse {
    private String email;

    private String password;
}
