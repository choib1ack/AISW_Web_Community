package com.aisw.community.repository;

import com.aisw.community.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomCommentRepository {
    List<Comment> findCommentByBoardId(Long id);
}
