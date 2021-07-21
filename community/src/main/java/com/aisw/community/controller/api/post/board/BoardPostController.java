package com.aisw.community.controller.api.post.board;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import com.aisw.community.service.post.board.BoardPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@CrossOrigin("*")
public abstract class BoardPostController<Req, FileReq, ListRes, DetailRes, BaseRes, Entity> implements CrudInterface<Req, BaseRes> {

    @Autowired(required = false)
    protected BoardPostService<Req, FileReq, ListRes, DetailRes, BaseRes, Entity> boardPostService;

    @Override
    @PostMapping("")
    public Header<BaseRes> create(@RequestBody Header<Req> request) {
        return boardPostService.create(request);
    }

    @PostMapping("/upload")
    public Header<BaseRes> create(@ModelAttribute FileReq request) {
        return boardPostService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<BaseRes> read(@PathVariable Long id) {
        return boardPostService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<BaseRes> update(@RequestBody Header<Req> request) {
        return boardPostService.update(request);
    }

    @PutMapping("/upload")
    public Header<BaseRes> update(@ModelAttribute FileReq request) {
        return boardPostService.update(request);
    }

    @Override
    @DeleteMapping("{id}/{userId}")
    public Header delete(@PathVariable Long id, @PathVariable Long userId) {
        return boardPostService.delete(id, userId);
    }

    @GetMapping("/comment/{id}")
    public Header<DetailRes> readWithComment(@PathVariable Long id) {
        return boardPostService.readWithComment(id);
    }

    @GetMapping("/comment&like/{postId}/{accountId}")
    public Header<DetailRes> readWithCommentAndLike(
            @PathVariable Long postId, @PathVariable Long accountId) {
        return boardPostService.readWithCommentAndLike(postId, accountId);
    }

    @GetMapping("")
    public Header<ListRes> search(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardPostService.search(pageable);
    }

    @GetMapping("/search/writer")
    public Header<ListRes> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardPostService.searchByWriter(writer, pageable);
    }

    @GetMapping("/search/title")
    public Header<ListRes> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardPostService.searchByTitle(title, pageable);
    }

    @GetMapping("/search/title&content")
    public Header<ListRes> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardPostService.searchByTitleOrContent(title, content, pageable);
    }
}
