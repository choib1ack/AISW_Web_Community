package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Board;
import com.aisw.community.model.entity.Notice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class BoardRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void create() {
        Board board = Board.builder()
                .build();

        Board newBoard = boardRepository.save(board);
        System.out.println("newBoard: " + newBoard);
    }

    @Test
    @Transactional
    public void read() {
        Optional<Board> board = boardRepository.findById(1L);

        board.ifPresent(readBoard -> {
            readBoard.getFreeList().stream().forEach(free -> {
                System.out.println(free.getId());
            });
        });
    }

    @Test
    @Transactional
    public void update() {
        Optional<Board> board = boardRepository.findById(1L);

        board.ifPresent(readBoard -> {

            boardRepository.save(readBoard);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<Board> board = boardRepository.findById(1L);

        Assertions.assertTrue(board.isPresent());

        board.ifPresent(readBoard -> {
            boardRepository.delete(readBoard);
        });

        Optional<Board> deleteBoard = boardRepository.findById(1L);

        Assertions.assertFalse(deleteBoard.isPresent());
    }
}