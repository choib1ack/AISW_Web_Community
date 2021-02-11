package com.aisw.community.repository;

import com.aisw.community.model.entity.Free;
import com.aisw.community.model.entity.FreeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeCommentRepository extends JpaRepository<FreeComment, Long> {
    List<FreeComment> findAllByFree(Free free);
}
