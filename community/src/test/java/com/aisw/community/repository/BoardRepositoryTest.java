package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.controller.api.post.board.BoardApiController;
import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.repository.post.board.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class BoardRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardApiController boardApiController;

    @Test
    @Transactional
    public void readAll() {
        List boardList = boardRepository.findAll();
        boardList.stream().forEach(o -> {
            Board board = (Board) o;
            System.out.println(board);
        });
    }
}