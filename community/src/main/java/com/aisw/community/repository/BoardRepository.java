package com.aisw.community.repository;

import com.aisw.community.model.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository<T extends Board> extends JpaRepository<T, Long> {
}
