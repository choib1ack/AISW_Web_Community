package com.aisw.community.repository.admin;

import com.aisw.community.model.entity.admin.Banner;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.aisw.community.model.entity.admin.QBanner.banner;

@RequiredArgsConstructor
@Repository
public class CustomBannerRepositoryImpl implements CustomBannerRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Banner> findAllFetchJoinWithFile(Pageable pageable) {
        QueryResults<Banner> queryResults = jpaQueryFactory.selectFrom(banner)
                .orderBy(banner.startDate.asc(), banner.endDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }
}
