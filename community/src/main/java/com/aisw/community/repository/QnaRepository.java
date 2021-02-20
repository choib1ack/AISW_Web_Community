package com.aisw.community.repository;

import com.aisw.community.model.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {
    Page<Qna> findAllByWriterContaining(String writer, Pageable pageable);
    Page<Qna> findAllByTitleContaining(String title, Pageable pageable);
    Page<Qna> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
