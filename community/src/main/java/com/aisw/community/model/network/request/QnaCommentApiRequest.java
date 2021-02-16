package com.aisw.community.model.network.request;

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
public class QnaCommentApiRequest {

    private Long id;

    private String comment;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    private Long likes;

    private Long qnaId;

    private Long userId;
}
