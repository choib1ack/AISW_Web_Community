package com.aisw.community.controller.api.post.board;

import com.aisw.community.controller.api.post.BoardPostController;
import com.aisw.community.model.entity.post.board.Qna;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.board.QnaApiRequest;
import com.aisw.community.model.network.response.post.board.BoardResponseDTO;
import com.aisw.community.model.network.response.post.board.QnaApiResponse;
import com.aisw.community.model.network.response.post.board.QnaDetailApiResponse;
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
@RequestMapping("/board/qna")
public class QnaApiController extends BoardPostController<QnaApiRequest, BoardResponseDTO, QnaDetailApiResponse, QnaApiResponse, Qna> {

    @Autowired
    private QnaApiLogicService qnaApiLogicService;

    @GetMapping("/subject")
    public Header<BoardResponseDTO> searchBySubject(
            @RequestParam List<String> subject,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return qnaApiLogicService.searchBySubject(subject, pageable);
    }
}
