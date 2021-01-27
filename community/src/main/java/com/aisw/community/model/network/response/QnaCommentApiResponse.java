package com.aisw.community.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaCommentApiResponse {

    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private String createdBy;

    private Long isAnonymous;

    private Long likes;

    private Long qnaId;
}
