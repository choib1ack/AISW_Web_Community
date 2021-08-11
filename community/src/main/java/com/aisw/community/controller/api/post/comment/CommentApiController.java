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

    @PostMapping("/auth/free/{boardId}/comment")
    public Header<CommentApiResponse> createAtFree(Authentication authentication, @PathVariable Long boardId, @RequestBody Header<CommentApiRequest> request) {
        return commentApiLogicService.create(authentication, boardId, request);
    }

    @PostMapping("/auth-student/qna/{boardId}/comment")
    public Header<CommentApiResponse> createAtQna(Authentication authentication, @PathVariable Long boardId, @RequestBody Header<CommentApiRequest> request) {
        return commentApiLogicService.create(authentication, boardId, request);

    }    @PostMapping("/auth/job/{boardId}/comment")
    public Header<CommentApiResponse> createAtJob(Authentication authentication, @PathVariable Long boardId, @RequestBody Header<CommentApiRequest> request) {
        return commentApiLogicService.create(authentication, boardId, request);
    }

    @DeleteMapping("/auth/free/{boardId}/comment/{commentId}")
    public Header deleteAtFree(Authentication authentication, @PathVariable Long boardId, @PathVariable Long commentId) {
        return commentApiLogicService.delete(authentication, boardId, commentId);
    }

    @DeleteMapping("/auth-student/qna/{boardId}/comment/{commentId}")
    public Header deleteAtQna(Authentication authentication, @PathVariable Long boardId, @PathVariable Long commentId) {
        return commentApiLogicService.delete(authentication, boardId, commentId);
    }

    @DeleteMapping("/auth/job/{boardId}/comment/{commentId}")
    public Header deleteAtJob(Authentication authentication, @PathVariable Long boardId, @PathVariable Long commentId) {
        return commentApiLogicService.delete(authentication, boardId, commentId);
    }
}