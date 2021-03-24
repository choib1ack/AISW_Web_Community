package com.aisw.community.controller;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import com.aisw.community.service.NoticePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@CrossOrigin("*")
public abstract class NoticePostController<Req, ListRes, Res, Entity> implements CrudInterface<Req, Res> {

    @Autowired(required = false)
    protected NoticePostService<Req, ListRes, Res, Entity> noticePostService;

    @Override
    @PostMapping("")
    public Header<Res> create(@RequestBody Header<Req> request) {
        return noticePostService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<Res> read(@PathVariable Long id) {
        return noticePostService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<Res> update(@RequestBody Header<Req> request) {
        return noticePostService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return noticePostService.delete(id);
    }

    @GetMapping("")
    public Header<ListRes> search(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return noticePostService.search(pageable);
    }

    @GetMapping("/search/writer")
    public Header<ListRes> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return noticePostService.searchByWriter(writer, pageable);
    }

    @GetMapping("/search/title")
    public Header<ListRes> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return noticePostService.searchByTitle(title, pageable);
    }

    @GetMapping("/search/title&content")
    public Header<ListRes> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return noticePostService.searchByTitleOrContent(title, content, pageable);
    }
}
