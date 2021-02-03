package com.aisw.community.model.network.response;

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

    private String category;

    private String title;

    private String created_by;

    private LocalDateTime created_at;

    private Long views;
}
