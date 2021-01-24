package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.network.request.FreeApiRequest;
import com.aisw.community.model.network.response.FreeApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/free")
public class FreeApiController extends CrudController<FreeApiRequest, FreeApiResponse, Free> {
}
