package com.aisw.community.repository.admin;

import com.aisw.community.model.network.response.admin.SiteInformationByCategoryResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.aisw.community.model.entity.admin.QSiteCategory.siteCategory;
import static com.aisw.community.model.entity.admin.QSiteInformation.siteInformation;

@RequiredArgsConstructor
@Repository
public class CustomSiteInformationRepositoryImpl implements CustomSiteInformationRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SiteInformationByCategoryResponse> findAllByCategory() {
        return jpaQueryFactory.from(siteInformation)
                .select(Projections.constructor(SiteInformationByCategoryResponse.class,
                        siteCategory.id,
                        siteCategory.name,
                        siteInformation))
                .leftJoin(siteInformation.fileList)
                .rightJoin(siteCategory).on(siteCategory.id.eq(siteInformation.siteCategory.id))
                .fetchJoin()
                .orderBy(siteCategory.name.asc())
                .fetch();
    }
}
