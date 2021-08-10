package com.aisw.community.controller.api.post.notice;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.post.notice.University;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.notice.FileUploadToUniversityApiRequest;
import com.aisw.community.model.network.request.post.notice.UniversityApiRequest;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.model.network.response.post.notice.UniversityApiResponse;
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
public class UniversityApiController implements CrudInterface<UniversityApiRequest, UniversityApiResponse> {

    @Autowired(required = false)
    protected NoticePostService<UniversityApiRequest, FileUploadToUniversityApiRequest, NoticeResponseDTO, UniversityApiResponse, University> noticePostService;

    @Override
    @PostMapping("/auth-admin/notice/university")
    public Header<UniversityApiResponse> create(Authentication authentication, @RequestBody Header<UniversityApiRequest> request) {
        return noticePostService.create(authentication, request);
    }

    @PostMapping("/auth-admin/notice/university/upload")
    public Header<UniversityApiResponse> create(Authentication authentication, @ModelAttribute FileUploadToUniversityApiRequest request) {
        return noticePostService.create(authentication, request);
    }

    @Override
    @GetMapping("/auth/notice/university/{id}")
    public Header<UniversityApiResponse> read(@PathVariable Long id) {
        return noticePostService.read(id);
    }

    @Override
    @PutMapping("/auth-admin/notice/university")
    public Header<UniversityApiResponse> update(Authentication authentication, @RequestBody Header<UniversityApiRequest> request) {
        return noticePostService.update(authentication, request);
    }

    @PutMapping("/auth-admin/notice/university/upload")
    public Header<UniversityApiResponse> update(Authentication authentication, @ModelAttribute FileUploadToUniversityApiRequest request) {
        return noticePostService.update(authentication, request);
    }

    @Override
    @DeleteMapping("/auth-admin/notice/university/{id}")
    public Header delete(Authentication authentication, @PathVariable Long id) {
        return noticePostService.delete(authentication, id);
    }

    @GetMapping("/notice/university")
    public Header<NoticeResponseDTO> readAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return noticePostService.readAll(pageable);
    }

    @GetMapping("/notice/university/search/writer")
    public Header<NoticeResponseDTO> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return noticePostService.searchByWriter(writer, pageable);
    }

    @GetMapping("/notice/university/search/title")
    public Header<NoticeResponseDTO> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return noticePostService.searchByTitle(title, pageable);
    }

    @GetMapping("/notice/university/search/title&content")
    public Header<NoticeResponseDTO> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return noticePostService.searchByTitleOrContent(title, content, pageable);
    }
}
