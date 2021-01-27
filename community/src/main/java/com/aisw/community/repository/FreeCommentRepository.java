package com.aisw.community.repository;

import com.aisw.community.model.entity.FreeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeCommentRepository extends JpaRepository<FreeComment, Long> {

}
