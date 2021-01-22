package com.aisw.community.repository;

import com.aisw.community.model.entity.UniversityContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityContentRepository extends JpaRepository<UniversityContent, Long> {

//    Optional<UniversityContent> findByUniversityContentId(String universityContentId);
}
