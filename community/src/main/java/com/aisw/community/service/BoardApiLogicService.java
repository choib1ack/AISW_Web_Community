package com.aisw.community.service;

import com.aisw.community.model.entity.Board;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.BoardApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
