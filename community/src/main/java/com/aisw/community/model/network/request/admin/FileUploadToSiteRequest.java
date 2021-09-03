package com.aisw.community.model.network.request.admin;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadToSiteRequest {

    private SiteInformationApiRequest siteInformationApiRequest;

    private Long[] delFileIds;

    private MultipartFile[] files;
}