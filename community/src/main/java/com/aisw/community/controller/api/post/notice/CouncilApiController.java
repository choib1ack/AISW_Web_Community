package com.aisw.community.controller.api.post.notice;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.post.notice.Council;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.notice.CouncilApiRequest;
import com.aisw.community.model.network.request.post.notice.FileUploadToCouncilApiRequest;
import com.aisw.community.model.network.response.post.notice.CouncilApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
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
public class CouncilApiController implements CrudInterface<CouncilApiRequest, CouncilApiResponse> {

    @Autowired(required = false)
    protected NoticePostService<CouncilApiRequest, FileUploadToCouncilApiRequest, NoticeResponseDTO, CouncilApiResponse, Council> noticePostService;

    @Override
    @PostMapping("/auth-council/notice/council")
    public Header<CouncilApiResponse> create(@RequestBody Header<CouncilApiRequest> request) {
        return noticePostService.create(request);
    }

    @PostMapping("/auth-council/notice/council/upload")
    public Header<CouncilApiResponse> create(@ModelAttribute FileUploadToCouncilApiRequest request) {
        return noticePostService.create(request);
    }

    @Override
    @GetMapping("/auth-student/notice/council/{id}")
    public Header<CouncilApiResponse> read(@PathVariable Long id) {
        return noticePostService.read(id);
    }

    @Override
    @PutMapping("/auth-council/notice/council")
    public Header<CouncilApiResponse> update(@RequestBody Header<CouncilApiRequest> request) {
        return noticePostService.update(request);
    }

    @PutMapping("/auth-council/notice/council/upload")
    public Header<CouncilApiResponse> update(@ModelAttribute FileUploadToCouncilApiRequest request) {
        return noticePostService.update(request);
    }

    @Override
    @DeleteMapping("/auth-council/notice/council/{id}/{userId}")
    public Header delete(@PathVariable Long id, @PathVariable Long userId) {
        return noticePostService.delete(id, userId);
    }

    @GetMapping("/notice/council")
    public Header<NoticeResponseDTO> search(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return noticePostService.search(pageable);
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
