package com.aisw.community.controller.api.post.board;

import com.aisw.community.model.entity.post.board.Free;
import com.aisw.community.model.network.request.post.board.FileUploadToFreeDTO;
import com.aisw.community.model.network.request.post.board.FreeApiRequest;
import com.aisw.community.model.network.response.post.board.BoardResponseDTO;
import com.aisw.community.model.network.response.post.board.FreeApiResponse;
import com.aisw.community.model.network.response.post.board.FreeDetailApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/board/free")
public class FreeApiController extends BoardPostController<FreeApiRequest, FileUploadToFreeDTO, BoardResponseDTO, FreeDetailApiResponse, FreeApiResponse, Free> {
}
