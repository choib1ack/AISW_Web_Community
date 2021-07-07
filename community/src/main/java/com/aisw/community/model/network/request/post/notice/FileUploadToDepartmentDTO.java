package com.aisw.community.model.network.request.post.notice;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadToDepartmentDTO {

    private DepartmentApiRequest departmentApiRequest;

    private MultipartFile[] files;
}