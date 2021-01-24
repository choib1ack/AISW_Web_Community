package com.aisw.community.controller.api;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.BoardApiRequest;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.service.BoardApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
public class BoardApiController implements CrudInterface<BoardApiRequest, BoardApiResponse> {

    @Autowired
    private BoardApiLogicService boardApiLogicService;

    @Override
    @PostMapping("")
    public Header<BoardApiResponse> create(@RequestBody Header<BoardApiRequest> request) {
        return boardApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<BoardApiResponse> read(@PathVariable Long id) {
        return boardApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<BoardApiResponse> update(@RequestBody Header<BoardApiRequest> request) {
        return boardApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return boardApiLogicService.delete(id);
    }
}
