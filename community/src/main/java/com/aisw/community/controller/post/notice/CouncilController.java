package com.aisw.community.controller.post.notice;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.post.notice.Council;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.notice.CouncilApiRequest;
import com.aisw.community.model.network.request.post.notice.FileUploadToCouncilRequest;
import com.aisw.community.model.network.response.post.notice.CouncilApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.service.post.notice.NoticePostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CouncilController implements CrudInterface<CouncilApiRequest, CouncilApiResponse> {

    @Autowired(required = false)
    protected NoticePostService<CouncilApiRequest, FileUploadToCouncilRequest, NoticeResponseDTO, CouncilApiResponse, Council> noticePostService;

    @Override
    @PostMapping("/auth-council/notice/council")
    public Header<CouncilApiResponse> create(Authentication authentication, @RequestBody Header<CouncilApiRequest> request) {
        return noticePostService.create(authentication, request);
    }

    @PostMapping("/auth-council/notice/council/upload")
    public Header<CouncilApiResponse> create(Authentication authentication, @ModelAttribute FileUploadToCouncilRequest request) {
        return noticePostService.create(authentication, request);
    }

    @Override
    @GetMapping("/auth-student/notice/council/{id}")
    public Header<CouncilApiResponse> read(@PathVariable Long id) {
        return noticePostService.read(id);
    }

    @Override
    @PutMapping("/auth-council/notice/council")
    public Header<CouncilApiResponse> update(Authentication authentication, @RequestBody Header<CouncilApiRequest> request) {
        return noticePostService.update(authentication, request);
    }

    @PutMapping("/auth-council/notice/council/upload")
    public Header<CouncilApiResponse> update(Authentication authentication, @ModelAttribute FileUploadToCouncilRequest request) {
        return noticePostService.update(authentication, request);
    }

    @Override
    @DeleteMapping("/auth-council/notice/council/{id}")
    public Header delete(Authentication authentication, @PathVariable Long id) {
        return noticePostService.delete(authentication, id);
    }

    @GetMapping("/notice/council")
    public Header<NoticeResponseDTO> readAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return noticePostService.readAll(pageable);
    }

    @GetMapping("/notice/council/search/writer")
    public Header<NoticeResponseDTO> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return noticePostService.searchByWriter(writer, pageable);
    }

    @GetMapping("/notice/council/search/title")
    public Header<NoticeResponseDTO> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return noticePostService.searchByTitle(title, pageable);
    }

    @GetMapping("/notice/council/search/title&content")
    public Header<NoticeResponseDTO> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return noticePostService.searchByTitleOrContent(title, content, pageable);
    }
}
