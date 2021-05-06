package com.aisw.community.model.network.response.post.attachment;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AttachmentApiResponseDTO {

     private String originFileName;

     private String fileName;

     private String filePath;

     private String fileType;

     private Long fileSize;
}
