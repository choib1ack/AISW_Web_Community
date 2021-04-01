package com.aisw.community.model.network.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginApiRequest {
    private String email;

    private String password;
}
