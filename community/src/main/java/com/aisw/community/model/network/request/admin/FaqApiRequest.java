package com.aisw.community.model.network.request.admin;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaqApiRequest {

    private Long id;

    private String question;

    private String answer;
}