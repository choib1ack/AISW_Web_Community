package com.aisw.community.controller.post.board;

import com.aisw.community.component.advice.exception.PostStatusNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.controller.ControllerInterface;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.board.FileUploadToJobRequest;
import com.aisw.community.model.network.request.post.board.JobApiRequest;
import com.aisw.community.model.network.response.post.board.*;
import com.aisw.community.service.post.board.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class JobController implements ControllerInterface<JobApiRequest, JobApiResponse> {

    @Autowired
    private JobService jobService;
    @Override
    @PostMapping("/auth/board/job")
    public Header<JobApiResponse> create(Authentication authentication, @RequestBody Header<JobApiRequest> request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        JobApiRequest jobApiRequest = request.getData();
        if (jobApiRequest.getStatus().equals(BulletinStatus.URGENT)
                || jobApiRequest.getStatus().equals(BulletinStatus.NOTICE)) {
            throw new PostStatusNotSuitableException(jobApiRequest.getStatus().getTitle());
        }
        return jobService.create(principal.getUser(), jobApiRequest);
    }

    @PostMapping("/auth/board/job/upload")
    public Header<JobApiResponse> create(Authentication authentication, @ModelAttribute FileUploadToJobRequest request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if (request.getJobApiRequest().getStatus().equals(BulletinStatus.URGENT)
                || request.getJobApiRequest().getStatus().equals(BulletinStatus.NOTICE)) {
            throw new PostStatusNotSuitableException(request.getJobApiRequest().getStatus().getTitle());
        }

        return jobService.create(principal.getUser(), request.getJobApiRequest(), request.getFiles());
    }

    @Override
    @GetMapping("/board/job/{id}")
    public Header<JobApiResponse> read(@PathVariable Long id) {
        return jobService.read(id);
    }

    @Override
    @PutMapping("/auth/board/job")
    public Header<JobApiResponse> update(Authentication authentication, @RequestBody Header<JobApiRequest> request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        JobApiRequest jobApiRequest = request.getData();
        if (jobApiRequest.getStatus().equals(BulletinStatus.URGENT)
                || jobApiRequest.getStatus().equals(BulletinStatus.NOTICE)) {
            throw new PostStatusNotSuitableException(jobApiRequest.getStatus().getTitle());
        }
        return jobService.update(principal.getUser(), jobApiRequest);
    }

    @PutMapping("/auth/board/job/upload")
    public Header<JobApiResponse> update(Authentication authentication, @ModelAttribute FileUploadToJobRequest request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if (request.getJobApiRequest().getStatus().equals(BulletinStatus.URGENT)
                || request.getJobApiRequest().getStatus().equals(BulletinStatus.NOTICE)) {
            throw new PostStatusNotSuitableException(request.getJobApiRequest().getStatus().getTitle());
        }

        return jobService.update(principal.getUser(), request.getJobApiRequest(), request.getFiles());
    }

    @Override
    @DeleteMapping("/auth/board/job/{id}")
    public Header delete(Authentication authentication, @PathVariable Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return jobService.delete(principal.getUser(), id);
    }

    @GetMapping("/board/job/comment/{id}")
    public Header<JobDetailApiResponse> readWithComment(@PathVariable Long id) {
        return jobService.readWithComment(id);
    }

    @GetMapping("/auth/board/job/comment&like/{id}")
    public Header<JobDetailApiResponse> readWithCommentAndLike(Authentication authentication, @PathVariable Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return jobService.readWithCommentAndLike(principal.getUser(), id);
    }

    @GetMapping("/board/job")
    public Header<JobResponseDTO> readAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return jobService.readAll(pageable);
    }

    @GetMapping("/board/job/search/writer")
    public Header<JobResponseDTO> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return jobService.searchByWriter(writer, pageable);
    }

    @GetMapping("/board/job/search/title")
    public Header<JobResponseDTO> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return jobService.searchByTitle(title, pageable);
    }

    @GetMapping("/board/job/search/title&content")
    public Header<JobResponseDTO> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return jobService.searchByTitleOrContent(title, content, pageable);
    }
}
