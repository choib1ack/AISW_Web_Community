package com.aisw.community.model.network.request.admin;


import com.aisw.community.model.enumclass.InformationCategory;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CiteInformationApiRequest {

    private Long id;

    private String name;

    private String content;

    private Boolean publishStatus;

    private String linkUrl;

    private InformationCategory informationCategory;
}