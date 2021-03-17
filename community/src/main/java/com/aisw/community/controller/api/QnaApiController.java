package com.aisw.community.controller.api;

import com.aisw.community.controller.PostController;
import com.aisw.community.model.entity.Qna;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.QnaApiRequest;
import com.aisw.community.model.network.response.BoardResponse;
import com.aisw.community.model.network.response.QnaApiResponse;
import com.aisw.community.service.QnaApiLogicService;
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
public class QnaApiController extends PostController<QnaApiRequest, BoardResponse, QnaApiResponse, Qna> {

    @Autowired
    private QnaApiLogicService qnaApiLogicService;

    @GetMapping("/subject")
    public Header<BoardResponse> searchBySubject(
            @RequestParam List<String> subject,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return qnaApiLogicService.searchBySubject(subject, pageable);
    }

    @GetMapping("/likes/{id}")
    public Header<QnaApiResponse> pressLikes(@PathVariable Long id) {
        return qnaApiLogicService.pressLikes(id);
    }
}
