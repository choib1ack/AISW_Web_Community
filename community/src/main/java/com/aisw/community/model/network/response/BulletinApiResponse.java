package com.aisw.community.model.network.response;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BulletinApiResponse {

    private Long id;

    private FirstCategory firstCategory;

    private SecondCategory secondCategory;

    private String title;

    private BulletinStatus status;

    private String writer;

    private LocalDateTime createdAt;

    private Long views;
}
