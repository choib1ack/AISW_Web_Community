package com.aisw.community.controller.api.post.comment;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.comment.CommentApiRequest;
import com.aisw.community.model.network.response.post.comment.CommentApiResponse;
import com.aisw.community.service.post.comment.CommentApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/board/comment")
public class CommentApiController {

    @Autowired
    private CommentApiLogicService commentApiLogicService;

    @PostMapping("")
    public Header<CommentApiResponse> create(Authentication authentication, @RequestBody Header<CommentApiRequest> request) {
        return commentApiLogicService.create(authentication, request);
    }

    @DeleteMapping("{id}")
    public Header delete(Authentication authentication, @PathVariable Long id) {
        return commentApiLogicService.delete(authentication, id);
    }
}