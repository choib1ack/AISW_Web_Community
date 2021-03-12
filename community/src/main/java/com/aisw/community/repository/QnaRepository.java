package com.aisw.community.repository;

import com.aisw.community.model.entity.Free;
import com.aisw.community.model.entity.Qna;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {
    Page<Qna> findAllByWriterContaining(String writer, Pageable pageable);
    Page<Qna> findAllByTitleContaining(String title, Pageable pageable);
    Page<Qna> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Qna> findAllByStatusOrStatus(BulletinStatus status1, BulletinStatus status2, Pageable pageable);
    Page<Qna> findAllBySubject(String subject, Pageable pageable);
}