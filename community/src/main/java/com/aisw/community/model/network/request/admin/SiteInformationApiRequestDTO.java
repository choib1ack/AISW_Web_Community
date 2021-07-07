package com.aisw.community.model.network.request.admin;


import com.aisw.community.model.enumclass.InformationCategory;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteInformationApiRequestDTO {

    private SiteInformationApiRequest siteInformationApiRequest;

    private MultipartFile[] files;
}