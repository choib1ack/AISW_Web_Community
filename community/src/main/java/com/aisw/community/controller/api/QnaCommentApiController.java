package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.QnaComment;
import com.aisw.community.model.network.request.QnaCommentApiRequest;
import com.aisw.community.model.network.response.QnaCommentApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/qna")
// TODO
public class QnaCommentApiController extends CrudController<QnaCommentApiRequest, QnaCommentApiResponse, QnaComment> {
}