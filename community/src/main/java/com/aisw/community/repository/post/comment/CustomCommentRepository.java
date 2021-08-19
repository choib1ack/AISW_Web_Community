package com.aisw.community.repository.post.comment;

import com.aisw.community.model.entity.post.comment.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomCommentRepository {
    List<Comment> findCommentByBoardId(Long id);
}
