package com.aisw.community.controller.api.post.board;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.post.board.Free;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.board.FileUploadToFreeApiRequest;
import com.aisw.community.model.network.request.post.board.FreeApiRequest;
import com.aisw.community.model.network.response.post.board.BoardResponseDTO;
import com.aisw.community.model.network.response.post.board.FreeApiResponse;
import com.aisw.community.model.network.response.post.board.FreeDetailApiResponse;
import com.aisw.community.service.post.board.BoardPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/board/free")
public class FreeApiController implements CrudInterface<FreeApiRequest, FreeApiResponse> {

    @Autowired(required = false)
    protected BoardPostService<FreeApiRequest, FileUploadToFreeApiRequest, BoardResponseDTO, FreeDetailApiResponse, FreeApiResponse, Free> boardPostService;

    @Override
    @PostMapping("")
    public Header<FreeApiResponse> create(Authentication authentication, @RequestBody Header<FreeApiRequest> request) {
        return boardPostService.create(authentication, request);
    }

    @PostMapping("/upload")
    public Header<FreeApiResponse> create(Authentication authentication, @ModelAttribute FileUploadToFreeApiRequest request) {
        return boardPostService.create(authentication, request);
    }

    @Override
    @GetMapping("{id}")
    public Header<FreeApiResponse> read(@PathVariable Long id) {
        return boardPostService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<FreeApiResponse> update(Authentication authentication, @RequestBody Header<FreeApiRequest> request) {
        return boardPostService.update(authentication, request);
    }

    @PutMapping("/upload")
    public Header<FreeApiResponse> update(Authentication authentication, @ModelAttribute FileUploadToFreeApiRequest request) {
        return boardPostService.update(authentication, request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(Authentication authentication, @PathVariable Long id) {
        return boardPostService.delete(authentication, id);
    }

    @GetMapping("/comment/{id}")
    public Header<FreeDetailApiResponse> readWithComment(@PathVariable Long id) {
        return boardPostService.readWithComment(id);
    }

    @GetMapping("/comment&like/{id}")
    public Header<FreeDetailApiResponse> readWithCommentAndLike(Authentication authentication, @PathVariable Long id) {
        return boardPostService.readWithCommentAndLike(authentication, id);
    }

    @GetMapping("")
    public Header<BoardResponseDTO> search(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardPostService.search(pageable);
    }

    @GetMapping("/search/writer")
    public Header<BoardResponseDTO> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardPostService.searchByWriter(writer, pageable);
    }

    @GetMapping("/search/title")
    public Header<BoardResponseDTO> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardPostService.searchByTitle(title, pageable);
    }

    @GetMapping("/search/title&content")
    public Header<BoardResponseDTO> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardPostService.searchByTitleOrContent(title, content, pageable);
    }
}
