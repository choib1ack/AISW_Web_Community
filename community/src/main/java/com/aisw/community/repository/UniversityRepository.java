package com.aisw.community.repository;

import com.aisw.community.model.entity.University;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    Page<University> findAllByCreatedByContaining(String writer, Pageable pageable);
    Page<University> findAllByTitleContaining(String title, Pageable pageable);
    Page<University> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
