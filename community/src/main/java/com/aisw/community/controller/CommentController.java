package com.aisw.community.controller;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.QnaApiResponse;
import com.aisw.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@CrossOrigin("*")
public abstract class CommentController<Req, Res, Entity> {

    @Autowired(required = false)
    protected CommentService<Req, Res, Entity> commentService;

    @PostMapping("")
    public Header<Res> create(@RequestBody Header<Req> request) {
        return commentService.create(request);
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return commentService.delete(id);
    }

    @GetMapping("{id}")
    public Header<List<Res>> searchByPost(@PathVariable Long id, @PageableDefault(sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return commentService.searchByPost(id, pageable);
    }

    @GetMapping("/likes/{id}")
    public Header<Res> pressLikes(@PathVariable Long id) {
        return commentService.pressLikes(id);
    }
}
