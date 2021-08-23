package com.aisw.community.controller.post.board;

import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface BoardControllerInterface<Req, Res, DetailRes, BoardRes> {

    Header<Res> create(Authentication authentication, Header<Req> request);

    Header<DetailRes> read(Long id);

    Header<DetailRes> read(Authentication authentication, Long id);

    Header<Res> update(Authentication authentication, Header<Req> request);

    Header delete(Authentication authentication, Long id);

    Header<BoardRes> readAll(Pageable pageable);

    Header<BoardRes> searchByWriter(String writer, Pageable pageable);

    Header<BoardRes> searchByTitle(String title, Pageable pageable);

    Header<BoardRes> searchByTitleOrContent(String title, String content, Pageable pageable);
}
