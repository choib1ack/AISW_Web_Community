package com.aisw.community.repository;

import com.aisw.community.model.entity.QnaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaCommentRepository extends JpaRepository<QnaComment, Long> {

}
