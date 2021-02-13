package com.aisw.community.controller.api;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.FreeCommentApiRequest;
import com.aisw.community.model.network.response.FreeCommentApiResponse;
import com.aisw.community.service.FreeCommentApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board/free/comment")
// TODO
public class FreeCommentApiController {

    @Autowired
    private FreeCommentApiLogicService freeCommentApiLogicService;

    @PostMapping("")
    public Header<FreeCommentApiResponse> create(@RequestBody Header<FreeCommentApiRequest> request) {
        return freeCommentApiLogicService.create(request);
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return freeCommentApiLogicService.delete(id);
    }

    @GetMapping("{id}")
    public Header<List<FreeCommentApiResponse>> searchByFree(
            @PathVariable Long id,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return freeCommentApiLogicService.searchByPost(id, pageable);
    }
}