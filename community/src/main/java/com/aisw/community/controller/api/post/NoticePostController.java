package com.aisw.community.controller.api.post;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import com.aisw.community.service.post.notice.NoticePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Component
@CrossOrigin("*")
public abstract class NoticePostController<Req, FileReq, ListRes, Res, Entity> implements CrudInterface<Req, Res> {

    @Autowired(required = false)
    protected NoticePostService<Req, FileReq, ListRes, Res, Entity> noticePostService;

    @Override
    @PostMapping("")
    public Header<Res> create(@RequestBody Header<Req> request) {
        return noticePostService.create(request);
    }

    @PostMapping("/upload")
    public Header<Res> create(@ModelAttribute FileReq request) {
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

    @PutMapping("/upload")
    public Header<Res> update(@ModelAttribute FileReq request) {
        return noticePostService.update(request);
    }

    @Override
    @DeleteMapping("{id}/{userId}")
    public Header delete(@PathVariable Long id, @PathVariable Long userId) {
        return noticePostService.delete(id, userId);
    }

//    @PostMapping("crawl")
//    public void crawling(@RequestParam Long board_no) throws IOException{
//        noticePostService.crawling(board_no);
//    }
//
//    @PostMapping("write")
//    public Header<Res> write(@RequestPart MultipartFile[] files) throws IOException {
//        return noticePostService.write(files);
//    }
//
//    @GetMapping("download")
//    public ResponseEntity<Resource> download(@RequestParam Long id, @RequestParam String file_name) throws IOException{
//        return noticePostService.download(id, file_name);
//    }

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
