package com.aisw.community.model.network.request;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.NoticeCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeApiRequest {

    private Long id;

    private NoticeCategory category;
}
