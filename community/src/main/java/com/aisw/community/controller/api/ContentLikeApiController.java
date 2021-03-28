package com.aisw.community.controller.api;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.CommentApiRequest;
import com.aisw.community.model.network.response.CommentApiResponse;
import com.aisw.community.service.ContentLikeApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/like")
public class ContentLikeApiController {

    @Autowired
    private ContentLikeApiLogicService contentLikeApiLogicService;

    @PostMapping("")
    public Header<CommentApiResponse> create(@RequestBody Header<CommentApiRequest> request) {
        return contentLikeApiLogicService.create(request);
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return contentLikeApiLogicService.delete(id);
    }

    @GetMapping("{id}")
    public Header<List<CommentApiResponse>> searchByPost(@PathVariable Long id) {
        return contentLikeApiLogicService.searchByPost(id);
    }

    @GetMapping("/likes/{id}")
    public Header<CommentApiResponse> pressLikes(@PathVariable Long id) {
        return contentLikeApiLogicService.pressLikes(id);
    }
}