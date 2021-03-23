package com.aisw.community.model.network.response;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaWithCommentApiResponse {

    private Long id;

    private String title;

    private String writer;

    private String content;

    private String attachmentFile;

    private BulletinStatus status;

    private Long views;

    private Long likes;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    // 학교 공지 0
    private Long level;

    private String subject;

    private SecondCategory category;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private Long userId;

    private List<CommentApiResponse> commentApiResponseList;
}
