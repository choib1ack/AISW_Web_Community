package com.aisw.community.controller.api;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.model.network.response.NoticeListApiResponse;
import com.aisw.community.service.NoticeApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notice")
public class NoticeApiController {

    @Autowired
    private NoticeApiLogicService noticeApiLogicService;

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
