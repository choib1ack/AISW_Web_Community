package com.aisw.community.service;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.Board;
import com.aisw.community.model.entity.Notice;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.BoardApiRequest;
import com.aisw.community.model.network.request.NoticeApiRequest;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.repository.BoardRepository;
import com.aisw.community.repository.NoticeRepository;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardApiLogicService implements CrudInterface<BoardApiRequest, BoardApiResponse> {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<BoardApiResponse> create(Header<BoardApiRequest> request) {
        BoardApiRequest boardApiRequest = request.getData();

        Board board = Board.builder()
                .user(userRepository.getOne(boardApiRequest.getUserId()))
                .build();
        Board newBoard = boardRepository.save(board);

        return response(newBoard);
    }

    @Override
    public Header<BoardApiResponse> read(Long id) {
        return boardRepository.findById(id)
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<BoardApiResponse> update(Header<BoardApiRequest> request) {
        BoardApiRequest boardApiRequest = request.getData();

        return boardRepository.findById(boardApiRequest.getId())
                .map(board -> {
                    board.setUser(userRepository.getOne(boardApiRequest.getUserId()));
                    return board;
                })
                .map(board -> boardRepository.save(board))
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return boardRepository.findById(id)
                .map(board -> {
                    boardRepository.delete(board);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private Header<BoardApiResponse> response(Board board) {
        BoardApiResponse boardApiResponse = BoardApiResponse.builder()
                .id(board.getId())
                .userId(board.getUser().getId())
                .build();

        return Header.OK(boardApiResponse);
    }
}
