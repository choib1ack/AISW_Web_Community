package com.aisw.community.repository.admin;

import com.aisw.community.model.entity.admin.SiteInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteInformationRepository extends JpaRepository<SiteInformation, Long> {

    @Query("select site from SiteInformation site left join fetch site.fileList")
    List<SiteInformation> findAllFetchJoinWithFile();
}