package com.aisw.community.controller.api.post.notice;

import com.aisw.community.model.entity.post.notice.Council;
import com.aisw.community.model.network.request.post.notice.CouncilApiRequest;
import com.aisw.community.model.network.request.post.notice.FileUploadToCouncilDTO;
import com.aisw.community.model.network.response.post.notice.CouncilApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notice/council")
public class CouncilApiController extends NoticePostController<CouncilApiRequest, FileUploadToCouncilDTO, NoticeResponseDTO, CouncilApiResponse, Council> {
}
