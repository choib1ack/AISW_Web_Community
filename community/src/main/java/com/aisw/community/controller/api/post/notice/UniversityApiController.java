package com.aisw.community.controller.api.post.notice;

import com.aisw.community.controller.api.post.NoticePostController;
import com.aisw.community.model.entity.post.notice.University;
import com.aisw.community.model.network.request.post.notice.UniversityApiRequest;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.model.network.response.post.notice.UniversityApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notice/university")
public class UniversityApiController extends NoticePostController<UniversityApiRequest, NoticeResponseDTO, UniversityApiResponse, University> {
}
