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
public class CommentApiController {

    @Autowired
    private CommentApiLogicService commentApiLogicService;

    @PostMapping("/auth/free/comment")
    public Header<CommentApiResponse> createAtFree(Authentication authentication, @RequestBody Header<CommentApiRequest> request) {
        return commentApiLogicService.create(authentication, request);
    }

    @PostMapping("/auth-student/qna/comment")
    public Header<CommentApiResponse> createAtQna(Authentication authentication, @RequestBody Header<CommentApiRequest> request) {
        return commentApiLogicService.create(authentication, request);
    }

    @PostMapping("/auth/job/comment")
    public Header<CommentApiResponse> createAtJob(Authentication authentication, @RequestBody Header<CommentApiRequest> request) {
        return commentApiLogicService.create(authentication, request);
    }

    @DeleteMapping("/auth/free/comment/{id}")
    public Header deleteAtFree(Authentication authentication, @PathVariable Long id) {
        return commentApiLogicService.delete(authentication, id);
    }

    @DeleteMapping("/auth-student/qna/comment/{id}")
    public Header deleteAtQna(Authentication authentication, @PathVariable Long id) {
        return commentApiLogicService.delete(authentication, id);
    }

    @DeleteMapping("/auth/job/comment/{id}")
    public Header deleteAtJob(Authentication authentication, @PathVariable Long id) {
        return commentApiLogicService.delete(authentication, id);
    }
}