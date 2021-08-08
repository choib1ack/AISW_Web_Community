package com.aisw.community.model.network.response.post.file;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     private LocalDateTime createdAt;

     private String createdBy;

     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonSerialize(using = LocalDateTimeSerializer.class)
     private LocalDateTime updatedAt;

     private String updatedBy;
}
