package com.aisw.community.service.post.board;

import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.service.post.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BoardPostService<Req, Res, ListRes, DetailRes> extends PostService<Req, Res, ListRes> {

    Header<DetailRes> read(Long id);

    Header<DetailRes> read(User user, Long id);
}
