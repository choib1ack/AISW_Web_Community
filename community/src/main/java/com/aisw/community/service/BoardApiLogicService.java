package com.aisw.community.service;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.AdminUser;
import com.aisw.community.model.entity.Board;
import com.aisw.community.model.entity.Notice;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.BoardApiRequest;
import com.aisw.community.model.network.request.NoticeApiRequest;
import com.aisw.community.model.network.response.*;
import com.aisw.community.repository.BoardRepository;
import com.aisw.community.repository.NoticeRepository;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardApiLogicService extends BaseService<BoardApiRequest, BoardApiResponse, Board> {

    @Override
    public Header<BoardApiResponse> create(Header<BoardApiRequest> request) {
        BoardApiRequest boardApiRequest = request.getData();

        Board board = Board.builder()
                .build();
        Board newBoard = baseRepository.save(board);

        return Header.OK(response(newBoard));
    }

    @Override
    public Header<BoardApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<BoardApiResponse> update(Header<BoardApiRequest> request) {
//        BoardApiRequest boardApiRequest = request.getData();

//        return baseRepository.findById(boardApiRequest.getId())
//                .map(board -> {
//                    board.setUser(userRepository.getOne(boardApiRequest.getUserId()));
//                    return board;
//                })
//                .map(board -> baseRepository.save(board))
//                .map(this::response)
//                .map(Header::OK)
//                .orElseGet(() -> Header.ERROR("데이터 없음"));

        return Header.ERROR("데이터 없음");
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(board -> {
                    baseRepository.delete(board);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private BoardApiResponse response(Board board) {
        BoardApiResponse boardApiResponse = BoardApiResponse.builder()
                .id(board.getId())
                .build();

        return boardApiResponse;
    }

    @Override
    public Header<List<BoardApiResponse>> search(Pageable pageable) {
        Page<Board> boards = baseRepository.findAll(pageable);

        List<BoardApiResponse> boardApiResponseList = boards.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(boardApiResponseList);
    }

    public Header<List<BoardListApiResponse>> searchList(Pageable pageable) {
        Page<Board> boards = baseRepository.findAll(pageable);

        List<BoardListApiResponse> boardListApiResponseList = new ArrayList<>();

        boards.stream().map(board -> {
            board.getFreeList().stream().forEach(free -> {
                BoardListApiResponse boardListApiResponse = BoardListApiResponse.builder()
                        .id(free.getId())
                        .category("free")
                        .title(free.getTitle())
                        .created_by(free.getCreatedBy())
                        .created_at(free.getCreatedAt())
                        .views(free.getViews())
                        .build();

                boardListApiResponseList.add(boardListApiResponse);
            });
            board.getQnaList().stream().forEach(qna -> {
                BoardListApiResponse boardListApiResponse = BoardListApiResponse.builder()
                        .id(qna.getId())
                        .category("qna")
                        .title(qna.getTitle())
                        .created_by(qna.getCreatedBy())
                        .created_at(qna.getCreatedAt())
                        .views(qna.getViews())
                        .build();

                boardListApiResponseList.add(boardListApiResponse);
            });

            return boardListApiResponseList;
        })
                .collect(Collectors.toList());

        return Header.OK(boardListApiResponseList);
    }
}
