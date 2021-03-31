package com.aisw.community.controller.api;

import com.aisw.community.controller.BoardPostController;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.network.request.FreeApiRequest;
import com.aisw.community.model.network.response.BoardResponseDTO;
import com.aisw.community.model.network.response.FreeApiResponse;
import com.aisw.community.model.network.response.FreeDetailApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/board/free")
public class FreeApiController extends BoardPostController<FreeApiRequest, BoardResponseDTO, FreeDetailApiResponse, FreeApiResponse, Free> {
}
