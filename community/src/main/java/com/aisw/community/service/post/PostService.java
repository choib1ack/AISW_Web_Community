package com.aisw.community.service.post;

import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService<Req, Res, ListRes> {

    Header<Res> create(User user, Req request);

    Header<Res> create(User user, Req request, MultipartFile[] files);

    Header<Res> update(User user, Req request);

    Header<Res> update(User user, Req request, MultipartFile[] files, List<Long> delFileIdList);

    Header delete(User user, Long id);

    Header<ListRes> readAll(Pageable pageable);

    Header<ListRes> searchByWriter(String writer, Pageable pageable);

    Header<ListRes> searchByTitle(String title, Pageable pageable);

    Header<ListRes> searchByTitleOrContent(String title, String content, Pageable pageable);
}
