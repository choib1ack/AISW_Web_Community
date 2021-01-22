package com.aisw.community.repository;

import com.aisw.community.model.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

//    Optional<UniversityContent> findByUniversityContentId(String universityContentId);
}
