package com.aisw.community.model.network.request.admin;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadToBannerRequest {

    private BannerApiRequest bannerApiRequest;

    private MultipartFile[] files;
}