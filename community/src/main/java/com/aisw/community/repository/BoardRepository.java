package com.aisw.community.repository;

import com.aisw.community.model.entity.Board;
import com.aisw.community.model.entity.Free;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository<T extends Board> extends JpaRepository<T, Long> {

    Page<Board> findAllByWriterContaining(String writer, Pageable pageable);
    Page<Board> findAllByTitleContaining(String title, Pageable pageable);
    Page<Board> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
