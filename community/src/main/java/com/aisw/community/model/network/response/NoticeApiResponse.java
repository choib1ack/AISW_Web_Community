package com.aisw.community.model.network.response;

import com.aisw.community.model.entity.Council;
import com.aisw.community.model.entity.Department;
import com.aisw.community.model.entity.University;
import com.aisw.community.model.enumclass.NoticeCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeApiResponse {

    private Long id;

    private NoticeCategory category;

    private LocalDateTime cratedAt;
}
