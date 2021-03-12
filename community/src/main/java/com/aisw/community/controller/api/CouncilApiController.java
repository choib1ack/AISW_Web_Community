package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.controller.PostController;
import com.aisw.community.model.entity.Council;
import com.aisw.community.model.network.request.CouncilApiRequest;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.model.network.response.CouncilApiResponse;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.model.network.response.NoticeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notice/council")
public class CouncilApiController extends PostController<CouncilApiRequest, NoticeResponse, CouncilApiResponse, Council> {
}
