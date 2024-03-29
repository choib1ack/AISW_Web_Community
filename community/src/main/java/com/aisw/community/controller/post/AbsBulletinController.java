package com.aisw.community.controller.post;

import com.aisw.community.model.network.Header;
import com.aisw.community.service.post.AbsBulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
public abstract class AbsBulletinController<Res, Entity> {

    @Autowired(required = false)
    protected AbsBulletinService<Res, Entity> absBulletinService;

    @GetMapping("/search/writer")
    public Header<Res> searchByWriter(
            @RequestParam String writer,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return absBulletinService.searchByWriter(writer, pageable);
    }

    @GetMapping("/search/title")
    public Header<Res> searchByTitle(
            @RequestParam String title,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return absBulletinService.searchByTitle(title, pageable);
    }

    @GetMapping("/search/title&content")
    public Header<Res> searchByTitleOrContent(
            @RequestParam String title, @RequestParam String content,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return absBulletinService.searchByTitleOrContent(title, content, pageable);
    }
}
