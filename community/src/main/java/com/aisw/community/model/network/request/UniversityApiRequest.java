package com.aisw.community.model.network.request;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.Campus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UniversityApiRequest {

    private Long id;

    private String title;

    private String writer;

    private String content;

    // 긴급0, 상단고정1, 일반2
    private BulletinStatus status;

    // 학교 공지 0
    private Long level;

    private Campus campus;

    private Long accountId;
}
