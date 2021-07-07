package com.aisw.community.model.network.request.admin;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerApiRequestDTO {

    private BannerApiRequest bannerApiRequest;

    private MultipartFile[] files;
}