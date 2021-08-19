package com.aisw.community.repository.admin;

import com.aisw.community.model.entity.admin.SiteCategory;
import com.aisw.community.model.entity.admin.SiteInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface SiteCategoryRepository extends JpaRepository<SiteCategory, Long> {
    Optional<SiteCategory> findByName(String name);

    @Query("select category from SiteCategory category order by category.id asc")
    List<SiteCategory> findAll();
}