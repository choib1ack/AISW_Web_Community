package com.aisw.community.controller.api;

import com.aisw.community.controller.PostController;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.FreeApiRequest;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.model.network.response.FreeApiResponse;
import com.aisw.community.service.FreeApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/board/free")
public class FreeApiController extends PostController<FreeApiRequest, BoardApiResponse, FreeApiResponse, Free> {

    @Autowired
    private FreeApiLogicService freeApiLogicService;

    @GetMapping("/likes/{id}")
    public Header<FreeApiResponse> pressLikes(@PathVariable Long id) {
        return freeApiLogicService.pressLikes(id);
    }
}
