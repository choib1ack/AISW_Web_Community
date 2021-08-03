package com.aisw.community.controller.api.post.board;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.post.board.Qna;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.board.FileUploadToQnaApiRequest;
import com.aisw.community.model.network.request.post.board.QnaApiRequest;
import com.aisw.community.model.network.response.post.board.BoardResponseDTO;
import com.aisw.community.model.network.response.post.board.QnaApiResponse;
import com.aisw.community.model.network.response.post.board.QnaDetailApiResponse;
import com.aisw.community.service.post.board.BoardPostService;
import com.aisw.community.service.post.board.QnaApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping()
public class QnaApiController implements CrudInterface<QnaApiRequest, QnaApiResponse> {

    @Autowired
    private QnaApiLogicService qnaApiLogicService;

    @Autowired(required = false)
    protected BoardPostService<QnaApiRequest, FileUploadToQnaApiRequest, BoardResponseDTO, QnaDetailApiResponse, QnaApiResponse, Qna> boardPostService;

    @Override
    @PostMapping("/auth-student/board/qna")
    public Header<QnaApiResponse> create(@RequestBody Header<QnaApiRequest> request) {
        return boardPostService.create(request);
    }

    @PostMapping("/auth-student/board/qna/upload")
    public Header<QnaApiResponse> create(@ModelAttribute FileUploadToQnaApiRequest request) {
        return boardPostService.create(request);
    }

    @Override
    @GetMapping("/auth-student/board/qna/{id}")
    public Header<QnaApiResponse> read(@PathVariable Long id) {
        return boardPostService.read(id);
    }

    @Override
    @PutMapping("/auth-student/board/qna")
    public Header<QnaApiResponse> update(@RequestBody Header<QnaApiRequest> request) {
        return boardPostService.update(request);
    }

    @PutMapping("/auth-student/board/qna/upload")
    public Header<QnaApiResponse> update(@ModelAttribute FileUploadToQnaApiRequest request) {
        return boardPostService.update(request);
    }

    @Override
    @DeleteMapping("/auth-student/board/qna/{id}/{userId}")
    public Header delete(@PathVariable Long id, @PathVariable Long userId) {
        return boardPostService.delete(id, userId);
    }

    @GetMapping("/auth-student/board/qna/comment/{id}")
    public Header<QnaDetailApiResponse> readWithComment(@PathVariable Long id) {
        return boardPostService.readWithComment(id);
    }

    @GetMapping("/auth-student/board/qna/comment&like/{postId}/{accountId}")
    public Header<QnaDetailApiResponse> readWithCommentAndLike(
            @PathVariable Long postId, @PathVariable Long accountId) {
        return boardPostService.readWithCommentAndLike(postId, accountId);
    }

    @GetMapping("/board/qna")
    public Header<BoardResponseDTO> search(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardPostService.search(pageable);
    }

    @GetMapping("/board/qna/search/writer")
    public Header<BoardResponseDTO> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardPostService.searchByWriter(writer, pageable);
    }

    @GetMapping("/board/qna/search/title")
    public Header<BoardResponseDTO> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardPostService.searchByTitle(title, pageable);
    }

    @GetMapping("/board/qna/search/title&content")
    public Header<BoardResponseDTO> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return boardPostService.searchByTitleOrContent(title, content, pageable);
    }

    @GetMapping("/board/qna/subject")
    public Header<BoardResponseDTO> searchBySubject(
            @RequestParam List<String> subject,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return qnaApiLogicService.searchBySubject(subject, pageable);
    }
}
