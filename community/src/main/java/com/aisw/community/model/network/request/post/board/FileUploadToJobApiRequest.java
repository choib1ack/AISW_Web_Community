package com.aisw.community.model.network.request.post.board;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadToJobApiRequest {

    private JobApiRequest jobApiRequest;

    private MultipartFile[] files;
}