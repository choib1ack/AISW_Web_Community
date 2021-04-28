package com.aisw.community.repository;

import com.aisw.community.model.entity.ContentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentLikeRepository extends JpaRepository<ContentLike, Long> {
    @Query("select c from ContentLike c where c.account.id = :accountId and " +
            "(c.board is not null and c.board.id = :boardId) or " +
            "(c.comment is not null and c.comment.board.id = :boardId)")
    List<ContentLike> findByAccountId(Long accountId, Long boardId);
    Optional<ContentLike> findContentLikeByAccountIdAndBoardId(Long accountId, Long boardId);
    Optional<ContentLike> findContentLikeByAccountIdAndCommentId(Long accountId, Long commentId);
}