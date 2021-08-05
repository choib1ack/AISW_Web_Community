package com.aisw.community.repository.post.notice;

import com.aisw.community.model.entity.post.notice.Council;
import com.aisw.community.model.entity.post.notice.Notice;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository<T extends Notice> extends JpaRepository<T, Long> {
    Page<Notice> findAllByWriterContaining(String writer, Pageable pageable);

    Page<Notice> findAllByTitleContaining(String title, Pageable pageable);

    Page<Notice> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    Page<Notice> findAllByStatusOrStatus(BulletinStatus status1, BulletinStatus status2, Pageable pageable);

    List<Notice> findTop10ByOrderByCreatedAtDesc();
}
