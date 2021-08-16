package com.aisw.community.controller.post.like;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.like.ContentLikeApiRequest;
import com.aisw.community.model.network.response.post.like.ContentLikeApiResponse;
import com.aisw.community.service.post.like.ContentLikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/like")
public class ContentLikeController {

    @Autowired
    private ContentLikeService contentLikeService;

    @PostMapping("/press")
    public Header<ContentLikeApiResponse> pressLike(Authentication authentication, @RequestBody Header<ContentLikeApiRequest> request) {
        return contentLikeService.pressLike(authentication, request);
    }

    @DeleteMapping("remove/{id}")
    public Header removeLike(Authentication authentication, @PathVariable Long id, @RequestParam String target) {
        return contentLikeService.removeLike(authentication, id, target);
    }
}