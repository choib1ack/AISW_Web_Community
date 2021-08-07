package com.aisw.community.model.network.response.post.board;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponseDTO {

    private List<BoardApiResponse> boardApiReviewResponseList;
    private List<BoardApiResponse> boardApiResponseList;
}
