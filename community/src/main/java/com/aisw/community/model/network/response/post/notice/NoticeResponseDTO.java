package com.aisw.community.model.network.response.post.notice;

import lombok.*;

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
