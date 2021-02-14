package com.aisw.community.repository;

import com.aisw.community.model.entity.Free;
import com.aisw.community.model.entity.FreeComment;
import com.aisw.community.model.entity.QnaComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeCommentRepository extends JpaRepository<FreeComment, Long> {
    Page<FreeComment> findAllByFreeId(Long id, Pageable pageable);
}
