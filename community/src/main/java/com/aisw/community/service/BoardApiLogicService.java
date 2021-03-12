package com.aisw.community.service;

import com.aisw.community.model.entity.Board;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.model.network.response.BoardResponse;
import com.aisw.community.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardApiLogicService extends BulletinService<BoardResponse, Board> {

    @Autowired
    private BoardRepository boardRepository;

    public Header<BoardResponse> searchList(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boardsByStatus = searchByStatus(pageable);

        return getListHeader(boards, boardsByStatus);
    }

    @Override
    public Header<BoardResponse> searchByWriter(String writer, Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByWriterContaining(writer, pageable);
        Page<Board> boardsByStatus = searchByStatus(pageable);

        return getListHeader(boards, boardsByStatus);
    }

    @Override
    public Header<BoardResponse> searchByTitle(String title, Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByTitleContaining(title, pageable);
        Page<Board> boardsByStatus = searchByStatus(pageable);

        return getListHeader(boards, boardsByStatus);
    }

    @Override
    public Header<BoardResponse> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<Board> boardsByStatus = searchByStatus(pageable);

        return getListHeader(boards, boardsByStatus);
    }

    public Page<Board> searchByStatus(Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByStatusOrStatus(
                BulletinStatus.URGENT, BulletinStatus.NOTICE, pageable);

        return boards;
    }

    private Header<BoardResponse> getListHeader
            (Page<Board> boards, Page<Board> boardsByStatus) {
        BoardResponse boardResponse = BoardResponse.builder()
                .boardApiResponseList(boards.stream()
                        .map(board -> BoardApiResponse.builder()
                                .id(board.getId())
                                .title(board.getTitle())
                                .category(board.getCategory())
                                .createdAt(board.getCreatedAt())
                                .status(board.getStatus())
                                .views(board.getViews())
                                .writer(board.getWriter())
                                .build())
                        .collect(Collectors.toList()))
                .boardApiTopResponseList(boardsByStatus.stream()
                        .map(board -> BoardApiResponse.builder()
                                .id(board.getId())
                                .title(board.getTitle())
                                .category(board.getCategory())
                                .createdAt(board.getCreatedAt())
                                .status(board.getStatus())
                                .views(board.getViews())
                                .writer(board.getWriter())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        Pagination pagination = Pagination.builder()
                .totalElements(boards.getTotalElements())
                .totalPages(boards.getTotalPages())
                .currentElements(boards.getNumberOfElements())
                .currentPage(boards.getNumber())
                .build();

        return Header.OK(boardResponse, pagination);
    }
}
