package com.aisw.community.repository;

import com.aisw.community.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository<T extends Notice> extends JpaRepository<T, Long> {
//    Page<Notice> findAllById(Pageable pageable);
}
