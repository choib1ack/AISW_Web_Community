package com.aisw.community.model.network.response.post.board;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardResponseDTO {

    private List<BoardApiResponse> boardApiNoticeResponseList;
    private List<BoardApiResponse> boardApiUrgentResponseList;
    private List<BoardApiResponse> boardApiResponseList;
}
