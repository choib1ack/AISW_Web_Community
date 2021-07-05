package com.aisw.community.repository.admin;

import com.aisw.community.model.entity.admin.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    List<Banner> findAllByPublishStatus(Boolean publishStatus);
    Page<Banner> findAllByPublishStatus(Boolean publishStatus, Pageable pageable);
}