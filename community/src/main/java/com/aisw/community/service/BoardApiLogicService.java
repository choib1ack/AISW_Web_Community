package com.aisw.community.service;

import com.aisw.community.model.entity.Board;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.response.BoardApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardApiLogicService extends BulletinService<BoardApiResponse, Board> {

    @Override
    public Header<List<BoardApiResponse>> searchList(Pageable pageable) {
        return null;
    }

    @Override
    public Header<List<BoardApiResponse>> searchByWriter(String writer, Pageable pageable) {
        return null;
    }

    @Override
    public Header<List<BoardApiResponse>> searchByTitle(String title, Pageable pageable) {
        return null;
    }

    @Override
    public Header<List<BoardApiResponse>> searchByTitleOrContent(String title, String content, Pageable pageable) {
        return null;
    }

    private Header<List<BoardApiResponse>> getListHeader(Page<Board> boards) {
        List<BoardApiResponse> noticeApiResponseList = boards.stream()
                .map(board -> BoardApiResponse.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .category(board.getCategory())
                        .createdAt(board.getCreatedAt())
                        .status(board.getStatus())
                        .views(board.getViews())
                        .writer(board.getWriter())
                        .build())
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(boards.getTotalElements())
                .totalPages(boards.getTotalPages())
                .currentElements(boards.getNumberOfElements())
                .currentPage(boards.getNumber())
                .build();

        return Header.OK(noticeApiResponseList, pagination);
    }
}
