package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.Qna;
import com.aisw.community.model.network.request.QnaApiRequest;
import com.aisw.community.model.network.response.QnaApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/board/qna")
public class QnaApiController extends CrudController<QnaApiRequest, QnaApiResponse, Qna> {
}
