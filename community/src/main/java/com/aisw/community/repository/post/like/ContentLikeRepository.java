package com.aisw.community.repository.post.like;

import com.aisw.community.model.entity.post.like.ContentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentLikeRepository extends JpaRepository<ContentLike, Long> {
    List<ContentLike> findAllByUserId(Long userId);
    Optional<ContentLike> findContentLikeByUserIdAndBoardId(Long userId, Long boardId);
    Optional<ContentLike> findContentLikeByUserIdAndCommentId(Long userId, Long commentId);
}