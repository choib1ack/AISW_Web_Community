package com.aisw.community.repository.post.board;

import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.entity.post.board.Free;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BoardRepository<T extends Board> extends JpaRepository<T, Long> {

    List<Board> findTop10ByOrderByCreatedAtDesc();

    Page<Board> findAllByWriterContaining(String writer, Pageable pageable);

    Page<Board> findAllByTitleContaining(String title, Pageable pageable);

    Page<Board> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    Page<Board> findAllByStatusIn(List<BulletinStatus> statusList, Pageable pageable);

    @Query("select board from Board board left join fetch board.commentList where board.id = :id")
    Optional<Board> findByIdWithComment(Long id);
}
