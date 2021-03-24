package com.aisw.community.service;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public abstract class BoardPostService<Req, ListRes, CommentRes, BaseRes, Entity> implements CrudInterface<Req, BaseRes> {

    @Autowired(required = false)
    protected JpaRepository<Entity, Long> baseRepository;

    public abstract Header<CommentRes> readWithComment(Long id);
    public abstract Header<ListRes> search(Pageable pageable);
    public abstract Header<ListRes> searchByWriter(String writer, Pageable pageable);
    public abstract Header<ListRes> searchByTitle(String title, Pageable pageable);
    public abstract Header<ListRes> searchByTitleOrContent(String title, String content, Pageable pageable);
    public abstract Header<BaseRes> pressLikes(Long id);
}
