package com.aisw.community.model.network.request.post.board;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadToQnaRequest {

    private QnaApiRequest qnaApiRequest;
    
    private Long[] delFileIds;

    private MultipartFile[] files;
}