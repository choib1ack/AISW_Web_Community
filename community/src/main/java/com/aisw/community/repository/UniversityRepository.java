package com.aisw.community.repository;

import com.aisw.community.model.entity.Qna;
import com.aisw.community.model.entity.University;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    Page<University> findAllByWriterContaining(String writer, Pageable pageable);
    Page<University> findAllByTitleContaining(String title, Pageable pageable);
    Page<University> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<University> findAllByStatusOrStatus(BulletinStatus status1, BulletinStatus status2, Pageable pageable);
}
