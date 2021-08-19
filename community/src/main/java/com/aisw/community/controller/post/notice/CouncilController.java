package com.aisw.community.controller.post.notice;

import com.aisw.community.component.advice.exception.PostStatusNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.controller.ControllerInterface;
import com.aisw.community.model.entity.post.notice.Council;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.notice.CouncilApiRequest;
import com.aisw.community.model.network.request.post.notice.FileUploadToCouncilRequest;
import com.aisw.community.model.network.response.post.notice.CouncilApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.service.post.notice.CouncilService;
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
public class CouncilController implements ControllerInterface<CouncilApiRequest, CouncilApiResponse> {

    @Autowired
    private CouncilService councilService;

    @Override
    @PostMapping("/auth-council/notice/council")
    public Header<CouncilApiResponse> create(Authentication authentication, @RequestBody Header<CouncilApiRequest> request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        CouncilApiRequest councilApiRequest = request.getData();
        if (councilApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(councilApiRequest.getStatus().getTitle());
        }
        return councilService.create(principal.getUser(), councilApiRequest);
    }

    @PostMapping("/auth-council/notice/council/upload")
    public Header<CouncilApiResponse> create(Authentication authentication, @ModelAttribute FileUploadToCouncilRequest request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if (request.getCouncilApiRequest().getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(request.getCouncilApiRequest().getStatus().getTitle());
        }
        return councilService.create(principal.getUser(), request.getCouncilApiRequest(), request.getFiles());
    }

    @Override
    @GetMapping("/auth-student/notice/council/{id}")
    public Header<CouncilApiResponse> read(@PathVariable Long id) {
        return councilService.read(id);
    }

    @Override
    @PutMapping("/auth-council/notice/council")
    public Header<CouncilApiResponse> update(Authentication authentication, @RequestBody Header<CouncilApiRequest> request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        CouncilApiRequest councilApiRequest = request.getData();
        if (councilApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(councilApiRequest.getStatus().getTitle());
        }
        return councilService.update(principal.getUser(), councilApiRequest);
    }

    @PutMapping("/auth-council/notice/council/upload")
    public Header<CouncilApiResponse> update(Authentication authentication, @ModelAttribute FileUploadToCouncilRequest request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if (request.getCouncilApiRequest().getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(request.getCouncilApiRequest().getStatus().getTitle());
        }
        return councilService.update(principal.getUser(), request.getCouncilApiRequest(), request.getFiles());
    }

    @Override
    @DeleteMapping("/auth-council/notice/council/{id}")
    public Header delete(Authentication authentication, @PathVariable Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return councilService.delete(principal.getUser(), id);
    }

    @GetMapping("/notice/council")
    public Header<NoticeResponseDTO> readAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return councilService.readAll(pageable);
    }

    @GetMapping("/notice/council/search/writer")
    public Header<NoticeResponseDTO> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return councilService.searchByWriter(writer, pageable);
    }

    @GetMapping("/notice/council/search/title")
    public Header<NoticeResponseDTO> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return councilService.searchByTitle(title, pageable);
    }

    @GetMapping("/notice/council/search/title&content")
    public Header<NoticeResponseDTO> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return councilService.searchByTitleOrContent(title, content, pageable);
    }
}
