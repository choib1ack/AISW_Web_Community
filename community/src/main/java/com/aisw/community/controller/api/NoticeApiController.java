package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.Notice;
import com.aisw.community.model.network.request.NoticeApiRequest;
import com.aisw.community.model.network.response.NoticeApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notice")
public class NoticeApiController extends CrudController<NoticeApiRequest, NoticeApiResponse, Notice> {
}
