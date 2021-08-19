package com.aisw.community.repository.post.notice;

import com.aisw.community.model.entity.post.board.Free;
import com.aisw.community.model.entity.post.notice.Department;
import com.aisw.community.model.entity.post.notice.University;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, Long> {

    @Query("select university from University university left join fetch university.fileList where university.id = :id")
    Optional<University> findById(Long id);

    Page<University> findAllByWriterContaining(String writer, Pageable pageable);

    Page<University> findAllByTitleContaining(String title, Pageable pageable);

    Page<University> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    Page<University> findAllByStatusIn(List<BulletinStatus> statusList, Pageable pageable);
}
