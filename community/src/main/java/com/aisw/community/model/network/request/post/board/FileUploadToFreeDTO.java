package com.aisw.community.model.network.request.post.board;


import com.aisw.community.model.network.request.admin.BannerApiRequest;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadToFreeDTO {

    private FreeApiRequest freeApiRequest;

    private MultipartFile[] files;
}