package com.aisw.community.repository.post.board;

import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.entity.post.board.Free;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository<T extends Board> extends JpaRepository<T, Long> {
    Page<Board> findAllByWriterContaining(String writer, Pageable pageable);
    Page<Board> findAllByTitleContaining(String title, Pageable pageable);
    Page<Board> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Board> findAllByStatusOrStatus(BulletinStatus status1, BulletinStatus status2, Pageable pageable);
    List<Board> findTop10ByOrderByCreatedAtDesc();
}
