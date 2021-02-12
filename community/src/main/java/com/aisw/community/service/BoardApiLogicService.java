package com.aisw.community.service;

import com.aisw.community.model.entity.Board;
import com.aisw.community.model.enumclass.BoardCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.response.*;
import com.aisw.community.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardApiLogicService {

    @Autowired
    private BoardRepository boardRepository;

    public Header<BoardApiResponse> create() {
        Board board = Board.builder().build();
        Board newBoard = boardRepository.save(board);

        return Header.OK(response(newBoard));
    }

    public Header delete(Long id) {
        return boardRepository.findById(id)
                .map(board -> {
                    boardRepository.delete(board);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private BoardApiResponse response(Board board) {
        BoardApiResponse boardApiResponse = BoardApiResponse.builder()
                .id(board.getId())
                .cratedAt(board.getCreatedAt())
                .build();

        return boardApiResponse;
    }

    public Header<List<BoardListApiResponse>> searchList(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);

        List<BoardListApiResponse> boardListApiResponseList = new ArrayList<>();

        boards.stream().map(board -> {
            board.getFreeList().stream().forEach(free -> {
                BoardListApiResponse boardListApiResponse = BoardListApiResponse.builder()
                        .id(free.getId())
                        .category(BoardCategory.FREE)
                        .title(free.getTitle())
                        .status(free.getStatus())
                        .createdBy(free.getCreatedBy())
                        .createdAt(free.getCreatedAt())
                        .views(free.getViews())
                        .build();

                boardListApiResponseList.add(boardListApiResponse);
            });
            board.getQnaList().stream().forEach(qna -> {
                BoardListApiResponse boardListApiResponse = BoardListApiResponse.builder()
                        .id(qna.getId())
                        .category(BoardCategory.QNA)
                        .title(qna.getTitle())
                        .status(qna.getStatus())
                        .createdBy(qna.getCreatedBy())
                        .createdAt(qna.getCreatedAt())
                        .views(qna.getViews())
                        .build();

                boardListApiResponseList.add(boardListApiResponse);
            });

            return boardListApiResponseList;
        })
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(boards.getTotalElements())
                .totalPages(boards.getTotalPages())
                .currentElements(boards.getNumberOfElements())
                .currentPage(boards.getNumber())
                .build();

        return Header.OK(boardListApiResponseList, pagination);
    }
}
