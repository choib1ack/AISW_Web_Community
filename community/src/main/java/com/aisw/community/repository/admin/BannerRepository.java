package com.aisw.community.repository.admin;

import com.aisw.community.model.entity.admin.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    @Query("select banner from Banner banner left join fetch banner.fileList " +
            "where banner.publishStatus = :publishStatus order by banner.startDate, banner.endDate ")
    List<Banner> findAllByPublishStatus(Boolean publishStatus);

    @Query("select banner from Banner banner left join fetch banner.fileList where banner.id = :id")
    Optional<Banner> findById(Long id);
}