package com.aisw.community.repository.post.notice;

import com.aisw.community.model.entity.post.notice.University;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    Page<University> findAllByWriterContaining(String writer, Pageable pageable);
    Page<University> findAllByTitleContaining(String title, Pageable pageable);
    Page<University> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<University> findAllByStatusOrStatus(BulletinStatus status1, BulletinStatus status2, Pageable pageable);
}
