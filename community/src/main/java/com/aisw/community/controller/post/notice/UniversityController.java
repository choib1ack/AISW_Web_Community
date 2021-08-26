package com.aisw.community.controller.post.notice;

import com.aisw.community.component.advice.exception.PostStatusNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.notice.FileUploadToUniversityRequest;
import com.aisw.community.model.network.request.post.notice.UniversityApiRequest;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.model.network.response.post.notice.UniversityApiResponse;
import com.aisw.community.service.post.notice.UniversityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UniversityController implements NoticePostController<UniversityApiRequest, UniversityApiResponse, NoticeResponseDTO> {

    @Autowired
    private UniversityService universityService;

    @Override
    @PostMapping("/auth-admin/notice/university")
    public Header<UniversityApiResponse> create(Authentication authentication, @RequestBody Header<UniversityApiRequest> request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        UniversityApiRequest universityApiRequest = request.getData();
        if(universityApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(universityApiRequest.getStatus().getTitle());
        }
        return universityService.create(principal.getUser(), universityApiRequest);
    }

    @PostMapping("/auth-admin/notice/university/upload")
    public Header<UniversityApiResponse> create(Authentication authentication, @ModelAttribute FileUploadToUniversityRequest request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if(request.getUniversityApiRequest().getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(request.getUniversityApiRequest().getStatus().getTitle());
        }
        return universityService.create(principal.getUser(), request.getUniversityApiRequest(), request.getFiles());
    }

    @Override
    @GetMapping("/auth/notice/university/{id}")
    public Header<UniversityApiResponse> read(Authentication authentication, @PathVariable Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return universityService.read(principal.getUser(), id);
    }

    @Override
    @PutMapping("/auth-admin/notice/university")
    public Header<UniversityApiResponse> update(Authentication authentication, @RequestBody Header<UniversityApiRequest> request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        UniversityApiRequest universityApiRequest = request.getData();
        if(universityApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(universityApiRequest.getStatus().getTitle());
        }
        return universityService.update(principal.getUser(), universityApiRequest);
    }

    @PutMapping("/auth-admin/notice/university/upload")
    public Header<UniversityApiResponse> update(Authentication authentication, @ModelAttribute FileUploadToUniversityRequest request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if(request.getUniversityApiRequest().getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(request.getUniversityApiRequest().getStatus().getTitle());
        }
        return universityService.update(principal.getUser(), request.getUniversityApiRequest(), request.getFiles());
    }

    @Override
    @DeleteMapping("/auth-admin/notice/university/{id}")
    public Header delete(Authentication authentication, @PathVariable Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return universityService.delete(principal.getUser(), id);
    }

    @GetMapping("/notice/university")
    public Header<NoticeResponseDTO> readAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return universityService.readAll(pageable);
    }

    @GetMapping("/notice/university/search/writer")
    public Header<NoticeResponseDTO> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return universityService.searchByWriter(writer, pageable);
    }

    @GetMapping("/notice/university/search/title")
    public Header<NoticeResponseDTO> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return universityService.searchByTitle(title, pageable);
    }

    @GetMapping("/notice/university/search/title&content")
    public Header<NoticeResponseDTO> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return universityService.searchByTitleOrContent(title, content, pageable);
    }
}