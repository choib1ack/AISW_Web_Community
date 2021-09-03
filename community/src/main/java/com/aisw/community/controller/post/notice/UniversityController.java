package com.aisw.community.controller.post.notice;

import com.aisw.community.component.advice.exception.PostStatusNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.notice.FileUploadToUniversityRequest;
import com.aisw.community.model.network.request.post.notice.UniversityApiRequest;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.model.network.response.post.notice.UniversityApiResponse;
import com.aisw.community.service.post.file.FileService;
import com.aisw.community.service.post.notice.UniversityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class UniversityController implements NoticePostController<FileUploadToUniversityRequest, UniversityApiResponse, NoticeResponseDTO> {

    @Autowired
    private UniversityService universityService;

    @Autowired
    private FileService fileService;

    @Override
    @PostMapping("/auth-admin/notice/university")
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
    public Header<UniversityApiResponse> update(Authentication authentication, @ModelAttribute FileUploadToUniversityRequest request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if(request.getUniversityApiRequest().getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(request.getUniversityApiRequest().getStatus().getTitle());
        }
        List<Long> delFileIdList = null;
        if(request.getDelFileIds() != null) {
            delFileIdList = Arrays.asList(request.getDelFileIds());
        }
        return universityService.update(principal.getUser(), request.getUniversityApiRequest(), request.getFiles(), delFileIdList);
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

    @Override
    @GetMapping("/auth/notice/university/file/download/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) {
        return fileService.download(fileName, request);
    }
}