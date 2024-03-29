package com.aisw.community.controller.post.board;

import com.aisw.community.component.advice.exception.PostStatusNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.board.FileUploadToQnaRequest;
import com.aisw.community.model.network.request.post.board.QnaApiRequest;
import com.aisw.community.model.network.response.post.board.BoardResponseDTO;
import com.aisw.community.model.network.response.post.board.QnaApiResponse;
import com.aisw.community.model.network.response.post.board.QnaDetailApiResponse;
import com.aisw.community.service.post.board.QnaService;
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
@RequestMapping("/api")
public class QnaController implements BoardPostController<FileUploadToQnaRequest, QnaApiResponse, QnaDetailApiResponse, BoardResponseDTO> {

    @Autowired
    private QnaService qnaService;

    @Autowired
    private FileService fileService;

    @Override
    @PostMapping("/auth-student/board/qna")
    public Header<QnaApiResponse> create(Authentication authentication, @ModelAttribute FileUploadToQnaRequest request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if (request.getQnaApiRequest().getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(request.getQnaApiRequest().getStatus().getTitle());
        }
        return qnaService.create(principal.getUser(), request.getQnaApiRequest(), request.getFiles());
    }

    @Override
    @GetMapping("/auth-student/board/qna/comment/{id}")
    public Header<QnaDetailApiResponse> read(@PathVariable Long id) {
        return qnaService.read(id);
    }

    @Override
    @GetMapping("/auth-student/board/qna/comment&like/{id}")
    public Header<QnaDetailApiResponse> read(Authentication authentication, @PathVariable Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return qnaService.read(principal.getUser(), id);
    }

    @Override
    @PutMapping("/auth-student/board/qna")
    public Header<QnaApiResponse> update(Authentication authentication, @ModelAttribute FileUploadToQnaRequest request) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if (request.getQnaApiRequest().getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(request.getQnaApiRequest().getStatus().getTitle());
        }
        List<Long> delFileIdList = null;
        if(request.getDelFileIds() != null) {
            delFileIdList = Arrays.asList(request.getDelFileIds());
        }
        return qnaService.update(principal.getUser(), request.getQnaApiRequest(), request.getFiles(), delFileIdList);
    }

    @Override
    @DeleteMapping("/auth-student/board/qna/{id}")
    public Header delete(Authentication authentication, @PathVariable Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return qnaService.delete(principal.getUser(), id);
    }

    @Override
    @GetMapping("/board/qna")
    public Header<BoardResponseDTO> readAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return qnaService.readAll(pageable);
    }

    @Override
    @GetMapping("/board/qna/search/writer")
    public Header<BoardResponseDTO> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return qnaService.searchByWriter(writer, pageable);
    }

    @Override
    @GetMapping("/board/qna/search/title")
    public Header<BoardResponseDTO> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return qnaService.searchByTitle(title, pageable);
    }

    @Override
    @GetMapping("/board/qna/search/title&content")
    public Header<BoardResponseDTO> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return qnaService.searchByTitleOrContent(title, content, pageable);
    }

    @GetMapping("/board/qna/subject")
    public Header<BoardResponseDTO> searchBySubject(
            @RequestParam List<String> subject,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return qnaService.searchBySubject(subject, pageable);
    }

    @Override
    @GetMapping("/auth-studnet/board/qna/file/download/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) {
        return fileService.download(fileName, request);
    }
}
