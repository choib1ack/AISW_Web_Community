package com.aisw.community.model.network.response;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeResponseDTO {

    private List<NoticeApiResponse> noticeApiNoticeResponseList;
    private List<NoticeApiResponse> noticeApiUrgentResponseList;
    private List<NoticeApiResponse> noticeApiResponseList;
}
