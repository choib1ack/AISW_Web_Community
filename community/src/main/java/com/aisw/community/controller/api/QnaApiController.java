package com.aisw.community.controller.api;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.QnaApiRequest;
import com.aisw.community.model.network.response.QnaApiResponse;
import com.aisw.community.service.QnaApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/qna")
public class QnaApiController implements CrudInterface<QnaApiRequest, QnaApiResponse> {

    @Autowired
    private QnaApiLogicService qnaApiLogicService;

    @Override
    @PostMapping("")
    public Header<QnaApiResponse> create(@RequestBody Header<QnaApiRequest> request) {
        return qnaApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<QnaApiResponse> read(@PathVariable Long id) {
        return qnaApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<QnaApiResponse> update(@RequestBody Header<QnaApiRequest> request) {
        return qnaApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return qnaApiLogicService.delete(id);
    }
}
