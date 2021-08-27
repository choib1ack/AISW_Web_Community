package com.aisw.community.repository.post.board;

import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomBoardRepository {

    Page<Board> findAll(Pageable pageable);

    Page<Board> findAllByWriterContaining(String writer, Pageable pageable);

    Page<Board> findAllByTitleContaining(String title, Pageable pageable);

    Page<Board> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    Page<Board> findAllByStatusIn(List<BulletinStatus> statusList, Pageable pageable);
}
