package com.aisw.community.model.network.response;

import com.aisw.community.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentApiResponse {

    private Long id;

    private String writer;

    private String content;

    private LocalDateTime createdAt;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    private Long likes;

    private Long boardId;

    private Long userId;

    private List<CommentApiResponse> subComment = new ArrayList<>();

    public CommentApiResponse(Long id, String writer, String content, LocalDateTime createdAt, Boolean isAnonymous, Long likes, Long boardId, Long userId) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
        this.isAnonymous = isAnonymous;
        this.likes = likes;
        this.boardId = boardId;
        this.userId = userId;
    }

    public static CommentApiResponse convertCommentToDto(Comment comment) {
        return comment.getIsDeleted() == true ?
                new CommentApiResponse(comment.getId(), null, "삭제된 댓글입니다.", null,
                        null, null, null, null) :
                new CommentApiResponse(comment.getId(), comment.getWriter(), comment.getContent(),
                        comment.getCreatedAt(), comment.getIsAnonymous(), comment.getLikes(),
                        comment.getBoard().getId(), comment.getUser().getId());
    }
}
