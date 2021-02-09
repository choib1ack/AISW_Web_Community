package com.aisw.community.controller.api;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.model.network.response.BoardListApiResponse;
import com.aisw.community.service.BoardApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardApiController {

    @Autowired
    private BoardApiLogicService boardApiLogicService;

    @PostMapping("")
    public Header<BoardApiResponse> create() {
        return boardApiLogicService.create();
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return boardApiLogicService.delete(id);
    }

    @GetMapping("/main")
    public Header<List<BoardListApiResponse>> searchList(@PageableDefault(sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return boardApiLogicService.searchList(pageable);
    }
}
