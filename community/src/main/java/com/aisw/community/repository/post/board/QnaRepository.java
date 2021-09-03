package com.aisw.community.repository.post.board;

import com.aisw.community.model.entity.post.board.Free;
import com.aisw.community.model.entity.post.board.Qna;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface QnaRepository extends JpaRepository<Qna, Long> {

    @Query("select qna from Qna qna left join fetch qna.fileList where qna.id = :id")
    Optional<Qna> findById(Long id);

    Page<Qna> findAllByWriterContaining(String writer, Pageable pageable);

    Page<Qna> findAllByTitleContaining(String title, Pageable pageable);

    Page<Qna> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    List<Qna> findTop10ByStatusIn(List<BulletinStatus> statusList);

    Page<Qna> findAllBySubjectIn(List<String> subject, Pageable pageable);
}