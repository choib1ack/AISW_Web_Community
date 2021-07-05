package com.aisw.community.model.network.response.post.file;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FileApiResponse {

     private Long id;

     private String fileName;

     private String fileDownloadUri;

     private String fileType;

     private Long fileSize;

     private LocalDateTime createdAt;

     private String createdBy;

     private LocalDateTime updatedAt;

     private String updatedBy;
}
