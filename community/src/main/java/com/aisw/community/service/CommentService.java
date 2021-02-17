package com.aisw.community.service;

import com.aisw.community.model.network.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class CommentService<Req, Res, Entity> {

    @Autowired(required = false)
    protected JpaRepository<Entity, Long> baseRepository;

    public abstract Header<Res> create(Header<Req> request);
    public abstract Header delete(Long id);
    public abstract Header<List<Res>> searchByPost(Long id, Pageable pageable);
    public abstract Header<Res> pressLikes(Long id);
}
