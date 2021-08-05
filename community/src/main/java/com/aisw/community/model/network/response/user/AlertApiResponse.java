package com.aisw.community.model.network.response.user;


import com.aisw.community.model.enumclass.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class AlertApiResponse {

    private Long id;

    private FirstCategory firstCategory;

    private SecondCategory secondCategory;

    private AlertCategory alertCategory;

    private Long postId;

    private LocalDateTime createdAt;

    private Boolean checked;
}