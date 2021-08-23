package com.aisw.community.service;

import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import org.springframework.security.core.Authentication;

public interface ServiceInterface<Req, Res> {

    Header<Res> create(User user, Req request);

    Header<Res> read(Long id);

    Header<Res> update(User user, Req request);

    Header delete(User user, Long id);
}
