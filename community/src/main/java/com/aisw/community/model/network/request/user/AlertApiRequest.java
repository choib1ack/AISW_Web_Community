package com.aisw.community.model.network.request.user;

import com.aisw.community.model.enumclass.Gender;
import com.aisw.community.model.enumclass.Grade;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertApiRequest {

    private Long commentId;

    private Long contentLikeId;
}