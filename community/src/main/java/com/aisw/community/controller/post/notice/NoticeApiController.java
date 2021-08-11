package com.aisw.community.controller.post.notice;

import com.aisw.community.controller.post.BulletinController;
import com.aisw.community.model.entity.post.notice.Notice;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.service.post.notice.NoticeApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notice")
public class NoticeApiController extends BulletinController<NoticeResponseDTO, Notice> {

    @Autowired
    private NoticeApiLogicService noticeApiLogicService;

    @GetMapping("/main")
    public Header<NoticeResponseDTO> searchList(@PageableDefault(sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return noticeApiLogicService.readAll(pageable);
    }
}
