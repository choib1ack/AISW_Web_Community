package com.aisw.community.controller.post.notice;

import com.aisw.community.controller.post.PostController;
import com.aisw.community.model.network.Header;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface NoticePostController<Req, Res, ListRes> extends PostController<Req, Res, ListRes> {

    Header<Res> read(Authentication authentication, Long id);
}
