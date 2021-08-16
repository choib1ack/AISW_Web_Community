package com.aisw.community.model.network.request.post.notice;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadToCouncilRequest {

    private CouncilApiRequest councilApiRequest;

    private MultipartFile[] files;
}