//package com.aisw.community.service;
//
//import com.aisw.community.model.entity.Board;
//import com.aisw.community.model.enumclass.BoardCategory;
//import com.aisw.community.model.network.Header;
//import com.aisw.community.model.network.Pagination;
//import com.aisw.community.model.network.request.BoardApiRequest;
//import com.aisw.community.model.network.response.*;
//import com.aisw.community.repository.BoardRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class BoardApiLogicService extends BoardService<BoardListApiResponse, Board> {
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Override
//    public Header<List<BoardListApiResponse>> searchList(Pageable pageable) {
//        Page<Board> boards = boardRepository.findAll(pageable);
//
//        List<BoardListApiResponse> boardListApiResponseList = new ArrayList<>();
//
//        boards.stream().map(board -> {
//            if(board.getCategory() == BoardCategory.FREE) {
//                board.getFreeList().stream().forEach(free -> {
//                    BoardListApiResponse boardListApiResponse = BoardListApiResponse.builder()
//                            .id(free.getId())
//                            .category(BoardCategory.FREE)
//                            .title(free.getTitle())
//                            .status(free.getStatus())
//                            .writer(free.getWriter())
//                            .createdAt(free.getCreatedAt())
//                            .views(free.getViews())
//                            .build();
//
//                    boardListApiResponseList.add(boardListApiResponse);
//                });
//            }
//            else if(board.getCategory() == BoardCategory.QNA) {
//                board.getQnaList().stream().forEach(qna -> {
//                    BoardListApiResponse boardListApiResponse = BoardListApiResponse.builder()
//                            .id(qna.getId())
//                            .category(BoardCategory.QNA)
//                            .title(qna.getTitle())
//                            .status(qna.getStatus())
//                            .writer(qna.getWriter())
//                            .createdAt(qna.getCreatedAt())
//                            .views(qna.getViews())
//                            .build();
//
//                    boardListApiResponseList.add(boardListApiResponse);
//                });
//            }
//
//            return boardListApiResponseList;
//        })
//                .collect(Collectors.toList());
//
//        Pagination pagination = Pagination.builder()
//                .totalElements(boards.getTotalElements())
//                .totalPages(boards.getTotalPages())
//                .currentElements(boards.getNumberOfElements())
//                .currentPage(boards.getNumber())
//                .build();
//
//        return Header.OK(boardListApiResponseList, pagination);
//    }
//
//    @Override
//    public Header<List<BoardListApiResponse>> searchByWriter(String writer, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public Header<List<BoardListApiResponse>> searchByTitle(String title, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public Header<List<BoardListApiResponse>> searchByTitleOrContent(String title, String content, Pageable pageable) {
//        return null;
//    }
//}
