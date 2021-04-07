package com.aisw.community.controller.api;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.CommentApiRequest;
import com.aisw.community.model.network.request.ContentLikeApiRequest;
import com.aisw.community.model.network.response.CommentApiResponse;
import com.aisw.community.model.network.response.ContentLikeApiResponse;
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

    @PostMapping("/press")
    public Header<ContentLikeApiResponse> pressLike(@RequestBody Header<ContentLikeApiRequest> request) {
        return contentLikeApiLogicService.pressLike(request);
    }

    @PostMapping("remove")
    public Header removeLike(@RequestBody Header<ContentLikeApiRequest> request) {
        return contentLikeApiLogicService.removeLike(request);
    }
}