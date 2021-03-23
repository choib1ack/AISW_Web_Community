package com.aisw.community.controller.api;

import com.aisw.community.controller.NoticePostController;
import com.aisw.community.model.entity.University;
import com.aisw.community.model.network.request.UniversityApiRequest;
import com.aisw.community.model.network.response.NoticeResponseDTO;
import com.aisw.community.model.network.response.UniversityApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notice/university")
public class UniversityApiController extends NoticePostController<UniversityApiRequest, NoticeResponseDTO, UniversityApiResponse, University> {
}
