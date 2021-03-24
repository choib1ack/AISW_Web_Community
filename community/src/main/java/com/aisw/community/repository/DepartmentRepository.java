package com.aisw.community.repository;

import com.aisw.community.model.entity.Bulletin;
import com.aisw.community.model.entity.Council;
import com.aisw.community.model.entity.Department;
import com.aisw.community.model.entity.University;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Page<Department> findAllByWriterContaining(String writer, Pageable pageable);
    Page<Department> findAllByTitleContaining(String title, Pageable pageable);
    Page<Department> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Department> findAllByStatusOrStatus(BulletinStatus status1, BulletinStatus status2, Pageable pageable);
}
