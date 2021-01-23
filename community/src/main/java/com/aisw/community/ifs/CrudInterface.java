package com.aisw.community.ifs;

import com.aisw.community.model.network.Header;

public interface CrudInterface<Req, Res> {

    Header<Res> create(Header<Req> request);

    Header<Res> read(Long id);

    Header<Res> update(Header<Req> request);

    Header delete(Long id);

    // TODO: create, update return값을 Header로 변경
}
