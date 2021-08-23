package com.aisw.community.service.post.notice;

import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.service.post.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface NoticePostService<Req, Res, ListRes> extends PostService<Req, Res, ListRes> {

    Header<Res> read(User user, Long id);
}
