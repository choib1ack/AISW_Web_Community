package com.aisw.community.controller.api;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.NoticeApiRequest;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.service.NoticeApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
public class NoticeApiController implements CrudInterface<NoticeApiRequest, NoticeApiResponse> {

    @Autowired
    private NoticeApiLogicService noticeApiLogicService;

    @Override
    @PostMapping("")
    public Header<NoticeApiResponse> create(@RequestBody Header<NoticeApiRequest> request) {
        return noticeApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<NoticeApiResponse> read(@PathVariable Long id) {
        return noticeApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<NoticeApiResponse> update(@RequestBody Header<NoticeApiRequest> request) {
        return noticeApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return noticeApiLogicService.delete(id);
    }
}
