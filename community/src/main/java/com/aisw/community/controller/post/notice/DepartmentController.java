package com.aisw.community.controller.post.notice;

import com.aisw.community.component.advice.exception.PostStatusNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.notice.DepartmentApiRequest;
import com.aisw.community.model.network.request.post.notice.FileUploadToDepartmentRequest;
import com.aisw.community.model.network.response.post.notice.DepartmentApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.service.post.file.FileService;
import com.aisw.community.service.post.notice.DepartmentService;
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
@RequestMapping("/api")
public class DepartmentController implements NoticePostController<FileUploadToDepartmentRequest, DepartmentApiResponse, NoticeResponseDTO> {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private FileService fileService;

    @Override
    @PostMapping("/auth-admin/notice/department")
    public Header<DepartmentApiResponse> create(Authentication authentication, @ModelAttribute FileUploadToDepartmentRequest request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if(request.getDepartmentApiRequest().getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(request.getDepartmentApiRequest().getStatus().getTitle());
        }
        return departmentService.create(principal.getUser(), request.getDepartmentApiRequest(), request.getFiles());
    }

    @Override
    @GetMapping("/auth-student/notice/department/{id}")
    public Header<DepartmentApiResponse> read(Authentication authentication, @PathVariable Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return departmentService.read(principal.getUser(), id);
    }

    @Override
    @PutMapping("/auth-admin/notice/department")
    public Header<DepartmentApiResponse> update(Authentication authentication, @ModelAttribute FileUploadToDepartmentRequest request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if(request.getDepartmentApiRequest().getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(request.getDepartmentApiRequest().getStatus().getTitle());
        }
        List<Long> delFileIdList = null;
        if(request.getDelFileIds() != null) {
            delFileIdList = Arrays.asList(request.getDelFileIds());
        }
        return departmentService.update(principal.getUser(), request.getDepartmentApiRequest(), request.getFiles(), delFileIdList);
    }

    @Override
    @DeleteMapping("/auth-admin/notice/department/{id}")
    public Header delete(Authentication authentication, @PathVariable Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return departmentService.delete(principal.getUser(), id);
    }

    @GetMapping("/notice/department")
    public Header<NoticeResponseDTO> readAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return departmentService.readAll(pageable);
    }

    @GetMapping("/notice/department/search/writer")
    public Header<NoticeResponseDTO> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return departmentService.searchByWriter(writer, pageable);
    }

    @GetMapping("/notice/department/search/title")
    public Header<NoticeResponseDTO> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return departmentService.searchByTitle(title, pageable);
    }

    @GetMapping("/notice/department/search/title&content")
    public Header<NoticeResponseDTO> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return departmentService.searchByTitleOrContent(title, content, pageable);
    }

    @Override
    @GetMapping("/auth-student/notice/department/file/download/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) {
        return fileService.download(fileName, request);
    }
}
