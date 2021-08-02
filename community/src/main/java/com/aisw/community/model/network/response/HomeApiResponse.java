package com.aisw.community.model.network.response;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.network.response.admin.HomeBannerAndSiteResponse;
import com.aisw.community.model.network.response.post.board.BoardApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeApiResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeApiResponse {

    private List<BoardApiResponse> universityList;
    private List<BoardApiResponse> departmentList;
    private List<BoardApiResponse> councilList;
    private List<BoardApiResponse> qnaList;
    private List<BoardApiResponse> freeList;
    private List<HomeBannerAndSiteResponse> bannerList;
    private List<HomeBannerAndSiteResponse> siteList;
}
