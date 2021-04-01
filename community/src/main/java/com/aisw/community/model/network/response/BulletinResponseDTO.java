package com.aisw.community.model.network.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BulletinResponseDTO {

    private List<BulletinApiResponse> bulletinApiNoticeResponseList;
    private List<BulletinApiResponse> bulletinApiUrgentResponseList;
    private List<BulletinApiResponse> bulletinApiResponseList;
}
