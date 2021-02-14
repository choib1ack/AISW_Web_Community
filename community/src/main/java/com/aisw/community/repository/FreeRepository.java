package com.aisw.community.repository;

import com.aisw.community.model.entity.Department;
import com.aisw.community.model.entity.Free;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeRepository extends JpaRepository<Free, Long> {
    Page<Free> findAllByCreatedByContaining(String writer, Pageable pageable);
    Page<Free> findAllByTitleContaining(String title, Pageable pageable);
    Page<Free> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
