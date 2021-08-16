package com.aisw.community.model.network.request.post.notice;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadToDepartmentRequest {

    private DepartmentApiRequest departmentApiRequest;

    private MultipartFile[] files;
}