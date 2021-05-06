package com.aisw.community.repository.post.notice;

import com.aisw.community.model.entity.post.notice.Department;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Page<Department> findAllByWriterContaining(String writer, Pageable pageable);
    Page<Department> findAllByTitleContaining(String title, Pageable pageable);
    Page<Department> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Department> findAllByStatusOrStatus(BulletinStatus status1, BulletinStatus status2, Pageable pageable);
}
