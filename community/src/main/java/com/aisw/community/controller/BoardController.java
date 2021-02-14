package com.aisw.community.controller;

import com.aisw.community.model.network.Header;
import com.aisw.community.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@CrossOrigin("*")
public abstract class BoardController<Req, ListRes, Res, Entity> {

    @Autowired(required = false)
    protected BoardService<Req, ListRes, Res, Entity> boardService;

    @PostMapping("")
    public Header<Res> create(Header<Req> request) {
        return boardService.create(request);
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return boardService.delete(id);
    }

    @GetMapping("/main")
    public Header<List<ListRes>> searchList(@PageableDefault(sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return boardService.searchList(pageable);
    }

    @GetMapping("/search/writer")
    public Header<List<ListRes>> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardService.searchByWriter(writer, pageable);
    }

    @GetMapping("/search/title")
    public Header<List<ListRes>> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardService.searchByTitle(title, pageable);
    }

    @GetMapping("/search/title&content")
    public Header<List<ListRes>> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardService.searchByTitleOrContent(title, content, pageable);
    }
}
