//package com.aisw.community.controller;
//
//import com.aisw.community.ifs.CrudInterface;
//import com.aisw.community.model.network.Header;
//import com.aisw.community.service.BaseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Component
//@CrossOrigin("*")
//public abstract class CrudController<Req, Res, Entity> implements CrudInterface<Req, Res> {
//
//    @Autowired(required = false)
//    protected BaseService<Req, Res, Entity> baseService;
//
//    @Override
//    @PostMapping("")
//    public Header<Res> create(Authentication authentication,  @RequestBody Header<Req> request) {
//        return baseService.create(authentication, request);
//    }
//
//    @Override
//    @GetMapping("{id}")
//    public Header<Res> read(@PathVariable Long id) {
//        return baseService.read(id);
//    }
//
//    @Override
//    @PutMapping("")
//    public Header<Res> update(@RequestBody Header<Req> request) {
//        return baseService.update(request);
//    }
//
//    @Override
//    @DeleteMapping("{id}/{userId}")
//    public Header delete(@PathVariable Long id, @PathVariable Long userId) {
//        return baseService.delete(id, userId);
//    }
//
//    @GetMapping("")
//    public Header<List<Res>> search(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
//        return baseService.search(pageable);
//    }
//}
