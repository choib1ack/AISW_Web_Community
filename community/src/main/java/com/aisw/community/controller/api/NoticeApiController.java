package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.Notice;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.NoticeApiRequest;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.model.network.response.NoticeListApiResponse;
import com.aisw.community.service.NoticeApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notice")
public class NoticeApiController extends CrudController<NoticeApiRequest, NoticeApiResponse, Notice> {

    @Autowired
    private NoticeApiLogicService noticeApiLogicService;

    @GetMapping("/main")
    public Header<List<NoticeListApiResponse>> searchList(@PageableDefault(sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable) {
            return noticeApiLogicService.searchList(pageable);
    }
}
