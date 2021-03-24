package com.aisw.community.controller.api;

import com.aisw.community.controller.BoardPostController;
import com.aisw.community.controller.NoticePostController;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.FreeApiRequest;
import com.aisw.community.model.network.response.BoardResponseDTO;
import com.aisw.community.model.network.response.FreeApiResponse;
import com.aisw.community.model.network.response.FreeWithCommentApiResponse;
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
public class FreeApiController extends BoardPostController<FreeApiRequest, BoardResponseDTO, FreeWithCommentApiResponse, FreeApiResponse, Free> {
}
