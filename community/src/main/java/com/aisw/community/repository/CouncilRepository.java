package com.aisw.community.repository;

import com.aisw.community.model.entity.Council;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouncilRepository extends JpaRepository<Council, Long> {

}
