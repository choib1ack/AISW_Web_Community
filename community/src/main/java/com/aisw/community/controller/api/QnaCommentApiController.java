package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.QnaComment;
import com.aisw.community.model.network.request.QnaCommentApiRequest;
import com.aisw.community.model.network.response.QnaCommentApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/board/qna/comment")
// TODO
public class QnaCommentApiController extends CrudController<QnaCommentApiRequest, QnaCommentApiResponse, QnaComment> {
}