package com.aisw.community.controller;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.model.network.response.NoticeListApiResponse;
import com.aisw.community.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@CrossOrigin("*")
public abstract class BoardController<Req, Res, Entity> implements CrudInterface<Req, Res> {

    @Autowired(required = false)
    protected PostService<Req, Res, Entity> postService;

    @PostMapping("")
    public Header<NoticeApiResponse> create() {
        return noticeApiLogicService.create();
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return noticeApiLogicService.delete(id);
    }

    @GetMapping("/main")
    public Header<List<NoticeListApiResponse>> searchList(@PageableDefault(sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return noticeApiLogicService.searchList(pageable);
    }
}
