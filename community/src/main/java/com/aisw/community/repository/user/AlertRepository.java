package com.aisw.community.repository.user;

import com.aisw.community.model.entity.user.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    Page<Alert> findAllByUserId(Long userId, Pageable pageable);
}
