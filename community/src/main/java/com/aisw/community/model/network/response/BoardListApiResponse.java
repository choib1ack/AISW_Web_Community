package com.aisw.community.model.network.response;

import com.aisw.community.model.enumclass.BoardCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardListApiResponse {

    private Long id;

    private BoardCategory category;

    private String title;

    private String createdBy;

    private LocalDateTime createdAt;

    private Long views;
}
