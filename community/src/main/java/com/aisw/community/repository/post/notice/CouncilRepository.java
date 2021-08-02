package com.aisw.community.repository.post.notice;

import com.aisw.community.model.entity.post.notice.Council;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouncilRepository extends JpaRepository<Council, Long> {

    @Query("select council from Council council left join fetch council.fileList where council.id = :id")
    Optional<Council> findById(Long id);

    Page<Council> findAllByWriterContaining(String writer, Pageable pageable);

    Page<Council> findAllByTitleContaining(String title, Pageable pageable);

    Page<Council> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    Page<Council> findAllByStatusOrStatus(BulletinStatus status1, BulletinStatus status2, Pageable pageable);

    List<Council> findTop10ByOrderByCreatedAtDesc();
}
