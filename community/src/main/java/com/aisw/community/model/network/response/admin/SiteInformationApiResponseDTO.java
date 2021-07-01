package com.aisw.community.model.network.response.admin;

import com.aisw.community.model.enumclass.InformationCategory;
import com.aisw.community.model.network.response.post.attachment.AttachmentApiResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SiteInformationApiResponseDTO {

     private InformationCategory category;

     private List<SiteInformationApiResponse> attachmentApiResponseList = new ArrayList<>();
}
