package com.aisw.community.service.post.notice;

import com.aisw.community.controller.ControllerInterface;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

public interface NoticePostService<Req, Res, ListRes> extends ServiceInterface<Req, Res> {

    Header<Res> create(User user, Req request, MultipartFile[] files);

    Header<Res> update(User user, Req request, MultipartFile[] files);

    Header<ListRes> readAll(Pageable pageable);

    Header<ListRes> searchByWriter(String writer, Pageable pageable);

    Header<ListRes> searchByTitle(String title, Pageable pageable);

    Header<ListRes> searchByTitleOrContent(String title, String content, Pageable pageable);
}
