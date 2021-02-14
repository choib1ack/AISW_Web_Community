package com.aisw.community.repository;

import com.aisw.community.model.entity.University;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    Page<University> findAllByCreatedByContaining(String writer, Pageable pageable);
    Page<University> findAllByTitleContaining(String title, Pageable pageable);
    Page<University> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    List<University> findAllByCreatedByContaining(String writer);
    List<University> findAllByTitleContaining(String title);
    List<University> findAllByTitleContainingOrContentContaining(String title, String content);
}
