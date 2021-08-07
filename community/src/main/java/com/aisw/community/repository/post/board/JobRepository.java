package com.aisw.community.repository.post.board;

import com.aisw.community.model.entity.post.board.Free;
import com.aisw.community.model.entity.post.board.Job;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("select job from Job job left join fetch job.fileList where job.id = :id")
    Optional<Job> findById(Long id);

    Page<Job> findAllByWriterContaining(String writer, Pageable pageable);

    Page<Job> findAllByTitleContaining(String title, Pageable pageable);

    Page<Job> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    Page<Job> findAllByStatus(BulletinStatus status, Pageable pageable);
}
