package com.aisw.community.service.post.board;

import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BoardPostService<Req, Res, ListRes, DetailRes> {

    Header<Res> create(User user, Req request);

    Header<Res> create(User user, Req request, MultipartFile[] files);

    Header<DetailRes> read(Long id);

    Header<DetailRes> read(User user, Long id);

    Header<Res> update(User user, Req request);

    Header<Res> update(User user, Req request, MultipartFile[] files);

    Header delete(User user, Long id);

    Header<ListRes> readAll(Pageable pageable);

    Header<ListRes> searchByWriter(String writer, Pageable pageable);

    Header<ListRes> searchByTitle(String title, Pageable pageable);

    Header<ListRes> searchByTitleOrContent(String title, String content, Pageable pageable);
}
