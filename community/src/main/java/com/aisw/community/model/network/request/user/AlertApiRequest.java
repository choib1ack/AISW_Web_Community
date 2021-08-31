package com.aisw.community.model.network.request.user;

import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.Gender;
import com.aisw.community.model.enumclass.Grade;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class AlertApiRequest {

    private Long commentId;

    private Long contentLikeId;

    private FirstCategory firstCategory;

    private SecondCategory secondCategory;

    private Long postId;

    private String content;

    private Long userId;
}