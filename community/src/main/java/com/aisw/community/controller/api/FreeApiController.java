package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.controller.PostController;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.network.request.FreeApiRequest;
import com.aisw.community.model.network.response.FreeApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/board/free")
public class FreeApiController extends PostController<FreeApiRequest, FreeApiResponse, Free> {
}
