package com.aisw.community.repository;

import com.aisw.community.model.entity.QnaComment;
import com.aisw.community.model.network.response.QnaCommentApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaCommentRepository extends JpaRepository<QnaComment, Long> {
    Page<QnaComment> findAllByQnaId(Long id, Pageable pageable);
}
