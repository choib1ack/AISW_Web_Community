package com.aisw.community.model.network.response;

import com.aisw.community.model.enumclass.BoardCategory;
import jdk.vm.ci.meta.Local;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardApiResponse {

    private Long id;

    private BoardCategory category;

    private LocalDateTime cratedAt;
}
