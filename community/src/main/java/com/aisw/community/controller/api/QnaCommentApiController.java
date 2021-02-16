package com.aisw.community.controller.api;

import com.aisw.community.controller.CommentController;
import com.aisw.community.model.entity.Qna;
import com.aisw.community.model.entity.QnaComment;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.QnaCommentApiRequest;
import com.aisw.community.model.network.response.QnaCommentApiResponse;
import com.aisw.community.service.QnaCommentApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board/qna/comment")
public class QnaCommentApiController extends CommentController<QnaCommentApiRequest, QnaCommentApiResponse, QnaComment> {
}