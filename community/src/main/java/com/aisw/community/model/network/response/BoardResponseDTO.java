package com.aisw.community.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardResponseDTO {

    private List<BoardApiResponse> boardApiNoticeResponseList;
    private List<BoardApiResponse> boardApiUrgentResponseList;
    private List<BoardApiResponse> boardApiResponseList;
}
