package com.aisw.community.service.post.notice;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public abstract class NoticePostService<Req, ListRes, Res, Entity> implements CrudInterface<Req, Res> {

    @Autowired(required = false)
    protected JpaRepository<Entity, Long> baseRepository;

//    public abstract void crawling(Long board_no) throws IOException;
//    public abstract Header<Res> write(MultipartFile[] files) throws IOException;
//    public abstract ResponseEntity<Resource> download(Long id, String originFileName) throws IOException;
    public abstract Header<ListRes> search(Pageable pageable);
    public abstract Header<ListRes> searchByWriter(String writer, Pageable pageable);
    public abstract Header<ListRes> searchByTitle(String title, Pageable pageable);
    public abstract Header<ListRes> searchByTitleOrContent(String title, String content, Pageable pageable);
}
