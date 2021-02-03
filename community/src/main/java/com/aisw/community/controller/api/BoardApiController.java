package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.Board;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.BoardApiRequest;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.model.network.response.BoardListApiResponse;
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
public class BoardApiController extends CrudController<BoardApiRequest, BoardApiResponse, Board> {

    @Autowired
    private BoardApiLogicService boardApiLogicService;

    @GetMapping("/main")
    public Header<List<BoardListApiResponse>> searchList(@PageableDefault(sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return boardApiLogicService.searchList(pageable);
    }
}
