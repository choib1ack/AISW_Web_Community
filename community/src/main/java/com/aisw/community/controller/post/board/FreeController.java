package com.aisw.community.controller.post.board;

import com.aisw.community.component.advice.exception.PostStatusNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.board.FileUploadToFreeRequest;
import com.aisw.community.model.network.request.post.board.FreeApiRequest;
import com.aisw.community.model.network.response.post.board.BoardResponseDTO;
import com.aisw.community.model.network.response.post.board.FreeApiResponse;
import com.aisw.community.model.network.response.post.board.FreeDetailApiResponse;
import com.aisw.community.service.post.board.FreeService;
import com.aisw.community.service.post.file.FileService;
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
public class FreeController implements BoardPostController<FreeApiRequest, FreeApiResponse, FreeDetailApiResponse, BoardResponseDTO> {

    @Autowired
    private FreeService freeService;

    @Autowired
    private FileService fileService;

    @Override
    @PostMapping("/auth/board/free")
    public Header<FreeApiResponse> create(Authentication authentication, @RequestBody Header<FreeApiRequest> request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        FreeApiRequest freeApiRequest = request.getData();
        if (freeApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(freeApiRequest.getStatus().getTitle());
        }
        return freeService.create(principal.getUser(), freeApiRequest);
    }

    @PostMapping("/auth/board/free/upload")
    public Header<FreeApiResponse> create(Authentication authentication, @ModelAttribute FileUploadToFreeRequest request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if (request.getFreeApiRequest().getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(request.getFreeApiRequest().getStatus().getTitle());
        }
        return freeService.create(principal.getUser(), request.getFreeApiRequest(), request.getFiles());
    }

    @Override
    @GetMapping("/board/free/comment/{id}")
    public Header<FreeDetailApiResponse> read(@PathVariable Long id) {
        return freeService.read(id);
    }

    @Override
    @GetMapping("/auth/board/free/comment&like/{id}")
    public Header<FreeDetailApiResponse> read(Authentication authentication, @PathVariable Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return freeService.read(principal.getUser(), id);
    }

    @Override
    @PutMapping("/auth/board/free")
    public Header<FreeApiResponse> update(Authentication authentication, @RequestBody Header<FreeApiRequest> request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        FreeApiRequest freeApiRequest = request.getData();
        if (freeApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(freeApiRequest.getStatus().getTitle());
        }
        return freeService.update(principal.getUser(), request.getData());
    }

    @PutMapping("/auth/board/free/upload")
    public Header<FreeApiResponse> update(Authentication authentication, @ModelAttribute FileUploadToFreeRequest request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if (request.getFreeApiRequest().getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(request.getFreeApiRequest().getStatus().getTitle());
        }
        List<Long> delFileIdList = null;
        if(request.getDelFileIds() != null) {
            delFileIdList = Arrays.asList(request.getDelFileIds());
            delFileIdList.stream().forEach(System.out::println);
        }
        return freeService.update(principal.getUser(), request.getFreeApiRequest(), request.getFiles(), delFileIdList);
    }

    @Override
    @DeleteMapping("/auth/board/free/{id}")
    public Header delete(Authentication authentication, @PathVariable Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return freeService.delete(principal.getUser(), id);
    }

    @Override
    @GetMapping("/board/free")
    public Header<BoardResponseDTO> readAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return freeService.readAll(pageable);
    }

    @Override
    @GetMapping("/board/free/search/writer")
    public Header<BoardResponseDTO> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return freeService.searchByWriter(writer, pageable);
    }

    @Override
    @GetMapping("/board/free/search/title")
    public Header<BoardResponseDTO> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return freeService.searchByTitle(title, pageable);
    }

    @Override
    @GetMapping("/board/free/search/title&content")
    public Header<BoardResponseDTO> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return freeService.searchByTitleOrContent(title, content, pageable);
    }

    @Override
    @GetMapping("/board/free/file/download/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) {
        return fileService.download(fileName, request);
    }
}
