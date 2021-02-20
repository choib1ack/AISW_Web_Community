package com.aisw.community.controller.api;

import com.aisw.community.controller.BulletinController;
import com.aisw.community.model.entity.Board;
import com.aisw.community.model.network.response.BoardApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardApiController extends BulletinController<BoardApiResponse, Board> {
}
