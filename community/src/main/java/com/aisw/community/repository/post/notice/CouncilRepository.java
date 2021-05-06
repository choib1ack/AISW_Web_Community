package com.aisw.community.repository.post.notice;

import com.aisw.community.model.entity.post.notice.Council;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouncilRepository extends JpaRepository<Council, Long> {
    Page<Council> findAllByWriterContaining(String writer, Pageable pageable);
    Page<Council> findAllByTitleContaining(String title, Pageable pageable);
    Page<Council> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Council> findAllByStatusOrStatus(BulletinStatus status1, BulletinStatus status2, Pageable pageable);
}
