package com.aisw.community.model.network.request.post.board;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadToJobRequest {

    private JobApiRequest jobApiRequest;

    private Long[] delFileIds;

    private MultipartFile[] files;
}