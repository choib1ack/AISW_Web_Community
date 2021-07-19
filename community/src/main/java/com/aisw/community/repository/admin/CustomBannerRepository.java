package com.aisw.community.repository.admin;

import com.aisw.community.model.entity.admin.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomBannerRepository {

    Page<Banner> findAllByPublishStatusFetchJoinWithFile(Boolean publishStatus, Pageable pageable);
}
