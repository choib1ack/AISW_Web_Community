package com.aisw.community.controller.post.board;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.post.board.Job;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.board.FileUploadToJobRequest;
import com.aisw.community.model.network.request.post.board.JobApiRequest;
import com.aisw.community.model.network.response.post.board.*;
import com.aisw.community.service.post.board.BoardPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class JobController implements CrudInterface<JobApiRequest, JobApiResponse> {

    @Autowired(required = false)
    protected BoardPostService<JobApiRequest, FileUploadToJobRequest, JobResponseDTO, JobDetailApiResponse, JobApiResponse, Job> boardPostService;

    @Override
    @PostMapping("/auth/board/job")
    public Header<JobApiResponse> create(Authentication authentication, @RequestBody Header<JobApiRequest> request) {
        return boardPostService.create(authentication, request);
    }

    @PostMapping("/auth/board/job/upload")
    public Header<JobApiResponse> create(Authentication authentication, @ModelAttribute FileUploadToJobRequest request) {
        return boardPostService.create(authentication, request);
    }

    @Override
    @GetMapping("/board/job/{id}")
    public Header<JobApiResponse> read(@PathVariable Long id) {
        return boardPostService.read(id);
    }

    @Override
    @PutMapping("/auth/board/job")
    public Header<JobApiResponse> update(Authentication authentication, @RequestBody Header<JobApiRequest> request) {
        return boardPostService.update(authentication, request);
    }

    @PutMapping("/auth/board/job/upload")
    public Header<JobApiResponse> update(Authentication authentication, @ModelAttribute FileUploadToJobRequest request) {
        return boardPostService.update(authentication, request);
    }

    @Override
    @DeleteMapping("/auth/board/job/{id}")
    public Header delete(Authentication authentication, @PathVariable Long id) {
        return boardPostService.delete(authentication, id);
    }

    @GetMapping("/board/job/comment/{id}")
    public Header<JobDetailApiResponse> readWithComment(@PathVariable Long id) {
        return boardPostService.readWithComment(id);
    }

    @GetMapping("/auth/board/job/comment&like/{id}")
    public Header<JobDetailApiResponse> readWithCommentAndLike(Authentication authentication, @PathVariable Long id) {
        return boardPostService.readWithCommentAndLike(authentication, id);
    }

    @GetMapping("/board/job")
    public Header<JobResponseDTO> readAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardPostService.readAll(pageable);
    }

    @GetMapping("/board/job/search/writer")
    public Header<JobResponseDTO> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardPostService.searchByWriter(writer, pageable);
    }

    @GetMapping("/board/job/search/title")
    public Header<JobResponseDTO> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardPostService.searchByTitle(title, pageable);
    }

    @GetMapping("/board/job/search/title&content")
    public Header<JobResponseDTO> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardPostService.searchByTitleOrContent(title, content, pageable);
    }
}
