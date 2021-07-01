package com.aisw.community.model.network.response.post.attachment;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AttachmentApiResponse {

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
