package com.aisw.community.repository;

import com.aisw.community.model.entity.Free;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeRepository extends JpaRepository<Free, Long> {

}
