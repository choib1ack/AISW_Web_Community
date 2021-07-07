package com.aisw.community.model.network.request.post.notice;


import com.aisw.community.model.network.request.post.board.FreeApiRequest;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadToCouncilDTO {

    private CouncilApiRequest councilApiRequest;

    private MultipartFile[] files;
}