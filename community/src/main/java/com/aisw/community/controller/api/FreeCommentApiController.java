package com.aisw.community.controller.api;

import com.aisw.community.controller.CommentController;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.entity.FreeComment;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.FreeCommentApiRequest;
import com.aisw.community.model.network.response.FreeCommentApiResponse;
import com.aisw.community.service.FreeCommentApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board/free/comment")
public class FreeCommentApiController extends CommentController<FreeCommentApiRequest, FreeCommentApiResponse, FreeComment> {
}