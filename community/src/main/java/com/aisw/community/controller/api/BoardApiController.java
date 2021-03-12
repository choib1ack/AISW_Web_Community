package com.aisw.community.controller.api;

import com.aisw.community.controller.BulletinController;
import com.aisw.community.model.entity.Board;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.model.network.response.BoardResponse;
import com.aisw.community.service.BoardApiLogicService;
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
@RequestMapping("/board")
public class BoardApiController extends BulletinController<BoardResponse, Board> {

    @Autowired
    private BoardApiLogicService boardApiLogicService;

    @GetMapping("/main")
    public Header<BoardResponse> searchList(@PageableDefault(sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return boardApiLogicService.searchList(pageable);
    }
}
