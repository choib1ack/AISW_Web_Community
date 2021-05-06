package com.aisw.community.repository.post;

import com.aisw.community.model.entity.post.Bulletin;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulletinRepository<T extends Bulletin> extends JpaRepository<T, Long> {
    Page<Bulletin> findAllByWriterContaining(String writer, Pageable pageable);
    Page<Bulletin> findAllByTitleContaining(String title, Pageable pageable);
    Page<Bulletin> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Bulletin> findAllByStatusOrStatus(BulletinStatus status1, BulletinStatus status2, Pageable pageable);
}
