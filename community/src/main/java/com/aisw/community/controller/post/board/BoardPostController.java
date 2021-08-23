package com.aisw.community.controller.post.board;

import com.aisw.community.controller.post.PostController;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface BoardPostController<Req, Res, DetailRes, ListRes> extends PostController<Req, Res, ListRes> {

    Header<DetailRes> read(Long id);

    Header<DetailRes> read(Authentication authentication, Long id);
}
