package com.aisw.community.controller.api;

import com.aisw.community.model.entity.Comment;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.CommentApiRequest;
import com.aisw.community.model.network.response.CommentApiResponse;
import com.aisw.community.service.CommentApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board/comment")
public class CommentApiController {

    @Autowired
    private CommentApiLogicService commentApiLogicService;

    @PostMapping("")
    public Header<CommentApiResponse> create(@RequestBody Header<CommentApiRequest> request) {
        return commentApiLogicService.create(request);
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return commentApiLogicService.delete(id);
    }

    @GetMapping("{id}")
    public Header<List<CommentApiResponse>> searchByPost(@PathVariable Long id) {
        return commentApiLogicService.searchByPost(id);
    }

    @GetMapping("/likes/{id}")
    public Header<CommentApiResponse> pressLikes(@PathVariable Long id) {
        return commentApiLogicService.pressLikes(id);
    }
}