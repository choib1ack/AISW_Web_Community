package com.aisw.community.repository.post.comment;

import com.aisw.community.model.entity.post.comment.Comment;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c left join fetch c.superComment where c.id = :id")
    Optional<Comment> findCommentByIdWithSuperComment(@Param("id") Long id);
}
