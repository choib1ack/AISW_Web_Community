package com.aisw.community.repository.admin;

import com.aisw.community.model.entity.admin.Banner;
import com.aisw.community.model.entity.admin.CiteInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiteInformationRepository extends JpaRepository<CiteInformation, Long> {

    List<CiteInformation> findAllByPublishStatus(Boolean publishStatus);
}