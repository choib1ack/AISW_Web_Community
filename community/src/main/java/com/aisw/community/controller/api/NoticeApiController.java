package com.aisw.community.controller.api;

import com.aisw.community.controller.BoardController;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notice")
public class NoticeApiController extends BoardController<NoticeApiRequest, NoticeListApiResponse, NoticeApiResponse, Notice> {
}
