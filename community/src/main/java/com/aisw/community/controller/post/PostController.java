package com.aisw.community.controller.post;

import com.aisw.community.model.network.Header;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

public interface PostController<Req, Res, ListRes> {

    Header<Res> create(Authentication authentication, Header<Req> request);

    Header<Res> update(Authentication authentication, Header<Req> request);

    Header delete(Authentication authentication, Long id);

    Header<ListRes> readAll(Pageable pageable);

    Header<ListRes> searchByWriter(String writer, Pageable pageable);

    Header<ListRes> searchByTitle(String title, Pageable pageable);

    Header<ListRes> searchByTitleOrContent(String title, String content, Pageable pageable);

    ResponseEntity<Resource> download(String fileName, HttpServletRequest request);
}
