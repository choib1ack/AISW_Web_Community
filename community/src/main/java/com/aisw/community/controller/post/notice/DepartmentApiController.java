package com.aisw.community.controller.post.notice;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.post.notice.Department;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.notice.DepartmentApiRequest;
import com.aisw.community.model.network.request.post.notice.FileUploadToDepartmentApiRequest;
import com.aisw.community.model.network.response.post.notice.DepartmentApiResponse;
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
public class DepartmentApiController implements CrudInterface<DepartmentApiRequest, DepartmentApiResponse> {

    @Autowired(required = false)
    protected NoticePostService<DepartmentApiRequest, FileUploadToDepartmentApiRequest, NoticeResponseDTO, DepartmentApiResponse, Department> noticePostService;

    @Override
    @PostMapping("/auth-admin/notice/department")
    public Header<DepartmentApiResponse> create(Authentication authentication, @RequestBody Header<DepartmentApiRequest> request) {
        return noticePostService.create(authentication, request);
    }

    @PostMapping("/auth-admin/notice/department/upload")
    public Header<DepartmentApiResponse> create(Authentication authentication, @ModelAttribute FileUploadToDepartmentApiRequest request) {
        return noticePostService.create(authentication, request);
    }

    @Override
    @GetMapping("/auth-student/notice/department/{id}")
    public Header<DepartmentApiResponse> read(@PathVariable Long id) {
        return noticePostService.read(id);
    }

    @Override
    @PutMapping("/auth-admin/notice/department")
    public Header<DepartmentApiResponse> update(Authentication authentication, @RequestBody Header<DepartmentApiRequest> request) {
        return noticePostService.update(authentication, request);
    }

    @PutMapping("/auth-admin/notice/department/upload")
    public Header<DepartmentApiResponse> update(Authentication authentication, @ModelAttribute FileUploadToDepartmentApiRequest request) {
        return noticePostService.update(authentication, request);
    }

    @Override
    @DeleteMapping("/auth-admin/notice/department/{id}")
    public Header delete(Authentication authentication, @PathVariable Long id) {
        return noticePostService.delete(authentication, id);
    }

    @GetMapping("/notice/department")
    public Header<NoticeResponseDTO> readAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return noticePostService.readAll(pageable);
    }

    @GetMapping("/notice/department/search/writer")
    public Header<NoticeResponseDTO> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return noticePostService.searchByWriter(writer, pageable);
    }

    @GetMapping("/notice/department/search/title")
    public Header<NoticeResponseDTO> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return noticePostService.searchByTitle(title, pageable);
    }

    @GetMapping("/notice/department/search/title&content")
    public Header<NoticeResponseDTO> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return noticePostService.searchByTitleOrContent(title, content, pageable);
    }
}
