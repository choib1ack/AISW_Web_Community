package com.aisw.community.controller;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import com.aisw.community.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@CrossOrigin("*")
public abstract class PostController<Req, ListRes, Res, Entity> implements CrudInterface<Req, Res> {

    @Autowired(required = false)
    protected PostService<Req, ListRes, Res, Entity> postService;

    @Override
    @PostMapping("")
    public Header<Res> create(@RequestBody Header<Req> request) {
        return postService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<Res> read(@PathVariable Long id) {
        return postService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<Res> update(@RequestBody Header<Req> request) {
        return postService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return postService.delete(id);
    }

    @GetMapping("")
    public Header<List<ListRes>> search(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.search(pageable);
    }

    @GetMapping("/search/writer")
    public Header<List<ListRes>> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return postService.searchByWriter(writer, pageable);
    }

    @GetMapping("/search/title")
    public Header<List<ListRes>> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return postService.searchByTitle(title, pageable);
    }

    @GetMapping("/search/title&content")
    public Header<List<ListRes>> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return postService.searchByTitleOrContent(title, content, pageable);
    }
}
