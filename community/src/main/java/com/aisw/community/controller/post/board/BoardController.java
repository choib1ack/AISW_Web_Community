package com.aisw.community.controller.post.board;

import com.aisw.community.controller.post.AbsBulletinController;
import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.post.board.BoardResponseDTO;
import com.aisw.community.service.post.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/board")
public class BoardController extends AbsBulletinController<BoardResponseDTO, Board> {

    @Autowired
    private BoardService boardService;

    @GetMapping("/main")
    public Header<BoardResponseDTO> readAll(@PageableDefault(sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return boardService.readAll(pageable);
    }
}
