package com.aisw.community.ifs;

import com.aisw.community.model.network.Header;
import org.springframework.security.core.Authentication;

public interface CrudInterface<Req, Res> {

    Header<Res> create(Authentication authentication, Header<Req> request);

    Header<Res> read(Long id);

    Header<Res> update(Authentication authentication, Header<Req> request);

    Header delete(Authentication authentication, Long id);
}
