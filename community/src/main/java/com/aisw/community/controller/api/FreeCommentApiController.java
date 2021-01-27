package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.FreeComment;
import com.aisw.community.model.network.request.FreeCommentApiRequest;
import com.aisw.community.model.network.response.FreeCommentApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/board/free/comment")
// TODO
public class FreeCommentApiController extends CrudController<FreeCommentApiRequest, FreeCommentApiResponse, FreeComment> {
}