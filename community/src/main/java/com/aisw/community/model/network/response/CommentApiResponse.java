package com.aisw.community.model.network.response;

import com.aisw.community.model.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    private Boolean checkLike = false;

    private Long boardId;

    private Long accountId;

    private List<CommentApiResponse> subComment = new ArrayList<>();

    public CommentApiResponse(Long id, String writer, String content, LocalDateTime createdAt, Boolean isAnonymous, Long likes, Long boardId, Long accountId) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
        this.isAnonymous = isAnonymous;
        this.likes = likes;
        this.boardId = boardId;
        this.accountId = accountId;
    }

    public static CommentApiResponse convertCommentToDto(Comment comment) {
        return comment.getIsDeleted() == true ?
                new CommentApiResponse(comment.getId(), null, "삭제된 댓글입니다.", null,
                        null, null, null, null) :
                new CommentApiResponse(comment.getId(), comment.getWriter(), comment.getContent(),
                        comment.getCreatedAt(), comment.getIsAnonymous(), comment.getLikes(),
                        comment.getBoard().getId(), comment.getAccount().getId());
    }
}
