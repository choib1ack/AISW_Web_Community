package com.aisw.community.service;

import com.aisw.community.model.network.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class BulletinService<Res, Entity> {

    @Autowired(required = false)
    protected JpaRepository<Entity, Long> baseRepository;

    public abstract Header<List<Res>> searchList(Pageable pageable);
    public abstract Header<List<Res>> searchByWriter(String writer, Pageable pageable);
    public abstract Header<List<Res>> searchByTitle(String title, Pageable pageable);
    public abstract Header<List<Res>> searchByTitleOrContent(String title, String content, Pageable pageable);
}
