package com.aisw.community.repository;

import com.aisw.community.model.entity.ContentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentLikeRepository extends JpaRepository<ContentLike, Long> {
}
