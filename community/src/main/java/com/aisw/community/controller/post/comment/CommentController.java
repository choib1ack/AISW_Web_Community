package com.aisw.community.controller.post.comment;

import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.comment.CommentApiRequest;
import com.aisw.community.model.network.response.post.comment.CommentApiResponse;
import com.aisw.community.service.post.comment.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/auth/free/{boardId}/comment")
    public Header<CommentApiResponse> createAtFree(Authentication authentication, @PathVariable Long boardId, @RequestBody Header<CommentApiRequest> request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return commentService.create(principal.getUser(), boardId, request.getData());
    }

    @PostMapping("/auth-student/qna/{boardId}/comment")
    public Header<CommentApiResponse> createAtQna(Authentication authentication, @PathVariable Long boardId, @RequestBody Header<CommentApiRequest> request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return commentService.create(principal.getUser(), boardId, request.getData());

    }

    @PostMapping("/auth/job/{boardId}/comment")
    public Header<CommentApiResponse> createAtJob(Authentication authentication, @PathVariable Long boardId, @RequestBody Header<CommentApiRequest> request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return commentService.create(principal.getUser(), boardId, request.getData());
    }

    @DeleteMapping("/auth/free/{boardId}/comment/{commentId}")
    public Header deleteAtFree(Authentication authentication, @PathVariable Long boardId, @PathVariable Long commentId) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return commentService.delete(principal.getUser(), boardId, commentId);
    }

    @DeleteMapping("/auth-student/qna/{boardId}/comment/{commentId}")
    public Header deleteAtQna(Authentication authentication, @PathVariable Long boardId, @PathVariable Long commentId) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return commentService.delete(principal.getUser(), boardId, commentId);
    }

    @DeleteMapping("/auth/job/{boardId}/comment/{commentId}")
    public Header deleteAtJob(Authentication authentication, @PathVariable Long boardId, @PathVariable Long commentId) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return commentService.delete(principal.getUser(), boardId, commentId);
    }
}