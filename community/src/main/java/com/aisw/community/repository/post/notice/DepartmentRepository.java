package com.aisw.community.repository.post.notice;

import com.aisw.community.model.entity.post.notice.Council;
import com.aisw.community.model.entity.post.notice.Department;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("select department from Department department left join fetch department.fileList where department.id = :id")
    Optional<Department> findById(Long id);

    Page<Department> findAllByWriterContaining(String writer, Pageable pageable);

    Page<Department> findAllByTitleContaining(String title, Pageable pageable);

    Page<Department> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    Page<Department> findAllByStatusOrStatus(BulletinStatus status1, BulletinStatus status2, Pageable pageable);

    List<Department> findTop10ByOrderByCreatedAtDesc();
}
