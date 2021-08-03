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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping()
public class UniversityApiController implements CrudInterface<UniversityApiRequest, UniversityApiResponse> {

    @Autowired(required = false)
    protected NoticePostService<UniversityApiRequest, FileUploadToUniversityApiRequest, NoticeResponseDTO, UniversityApiResponse, University> noticePostService;

    @Override
    @PostMapping("/auth-admin/notice/university")
    public Header<UniversityApiResponse> create(@RequestBody Header<UniversityApiRequest> request) {
        return noticePostService.create(request);
    }

    @PostMapping("/auth-admin/notice/university/upload")
    public Header<UniversityApiResponse> create(@ModelAttribute FileUploadToUniversityApiRequest request) {
        return noticePostService.create(request);
    }

    @Override
    @GetMapping("/auth-student/notice/university/{id}")
    public Header<UniversityApiResponse> read(@PathVariable Long id) {
        return noticePostService.read(id);
    }

    @Override
    @PutMapping("/auth-admin/notice/university")
    public Header<UniversityApiResponse> update(@RequestBody Header<UniversityApiRequest> request) {
        return noticePostService.update(request);
    }

    @PutMapping("/auth-admin/notice/university/upload")
    public Header<UniversityApiResponse> update(@ModelAttribute FileUploadToUniversityApiRequest request) {
        return noticePostService.update(request);
    }

    @Override
    @DeleteMapping("/auth-admin/notice/university/{id}/{userId}")
    public Header delete(@PathVariable Long id, @PathVariable Long userId) {
        return noticePostService.delete(id, userId);
    }

    @GetMapping("/notice/university")
    public Header<NoticeResponseDTO> search(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return noticePostService.search(pageable);
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
