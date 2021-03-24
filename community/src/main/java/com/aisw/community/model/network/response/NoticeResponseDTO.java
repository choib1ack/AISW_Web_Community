package com.aisw.community.model.network.response;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeResponseDTO {

    private List<NoticeApiResponse> noticeApiNoticeResponseList;
    private List<NoticeApiResponse> noticeApiUrgentResponseList;
    private List<NoticeApiResponse> noticeApiResponseList;
}
