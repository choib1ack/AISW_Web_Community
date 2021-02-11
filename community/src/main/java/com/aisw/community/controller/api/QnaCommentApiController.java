package com.aisw.community.controller.api;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.FreeCommentApiRequest;
import com.aisw.community.model.network.request.QnaCommentApiRequest;
import com.aisw.community.model.network.response.FreeCommentApiResponse;
import com.aisw.community.model.network.response.QnaApiResponse;
import com.aisw.community.model.network.response.QnaCommentApiResponse;
import com.aisw.community.service.QnaCommentApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board/qna/comment")
// TODO
public class QnaCommentApiController {

    @Autowired
    private QnaCommentApiLogicService qnaCommentApiLogicService;

    @PostMapping("")
    public Header<QnaCommentApiResponse> create(@RequestBody Header<QnaCommentApiRequest> request) {
        return qnaCommentApiLogicService.create(request);
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return qnaCommentApiLogicService.delete(id);
    }

    @GetMapping("{id}")
    public Header<List<QnaCommentApiResponse>> searchByFree(
            @PathVariable Long id,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return qnaCommentApiLogicService.searchByFree(id, pageable);
    }
}