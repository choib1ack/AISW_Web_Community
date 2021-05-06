package com.aisw.community.repository.post.board;

import com.aisw.community.model.entity.post.board.Free;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeRepository extends JpaRepository<Free, Long> {
    Page<Free> findAllByWriterContaining(String writer, Pageable pageable);
    Page<Free> findAllByTitleContaining(String title, Pageable pageable);
    Page<Free> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Free> findAllByStatusOrStatus(BulletinStatus status1, BulletinStatus status2, Pageable pageable);
}
