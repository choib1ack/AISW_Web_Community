package com.aisw.community.repository.post.board;

import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.entity.post.board.Free;
import com.aisw.community.model.entity.post.notice.University;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface FreeRepository extends JpaRepository<Free, Long> {

    @Query("select free from Free free left join fetch free.fileList where free.id = :id")
    Optional<Free> findById(Long id);

    Page<Free> findAllByWriterContaining(String writer, Pageable pageable);

    Page<Free> findAllByTitleContaining(String title, Pageable pageable);

    Page<Free> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    List<Free> findTop10ByStatusIn(List<BulletinStatus> statusList);
}
