package com.aisw.community.controller.post.notice;

import com.aisw.community.model.network.Header;
import org.springframework.security.core.Authentication;

public interface NoticeControllerInterface<Req, Res> {

    Header<Res> create(Authentication authentication, Header<Req> request);

    Header<Res> read(Authentication authentication, Long id);

    Header<Res> update(Authentication authentication, Header<Req> request);

    Header delete(Authentication authentication, Long id);
}
