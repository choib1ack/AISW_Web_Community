package com.aisw.community.repository;

import com.aisw.community.model.entity.Council;
import com.aisw.community.model.entity.Department;
import com.aisw.community.model.entity.University;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouncilRepository extends JpaRepository<Council, Long> {
    Page<Council> findAllByWriterContaining(String writer, Pageable pageable);
    Page<Council> findAllByTitleContaining(String title, Pageable pageable);
    Page<Council> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    List<Council> findAllByStatusContainingAndStatusContaining(BulletinStatus status1, BulletinStatus status2);
}
