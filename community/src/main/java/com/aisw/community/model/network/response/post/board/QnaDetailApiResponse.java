package com.aisw.community.model.network.response.post.board;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.network.response.post.comment.CommentApiResponse;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaDetailApiResponse {

    private Long id;

    private String title;

    private String writer;

    private String content;

    private BulletinStatus status;

    private Long views;

    private Long likes;

    private String subject;

    private SecondCategory category;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private Long userId;

    private Boolean checkLike = false;

    private Boolean isWriter;

    private List<FileApiResponse> fileApiResponseList;

    private List<CommentApiResponse> commentApiResponseList;
}
