package com.aisw.community.controller.admin;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.admin.SiteCategoryApiResponse;
import com.aisw.community.service.admin.SiteCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth-admin/site/category")
public class SiteCategoryController {

    @Autowired
    private SiteCategoryService siteCategoryService;

    @PostMapping("")
    public Header<SiteCategoryApiResponse> create(@RequestParam String name) {
        return siteCategoryService.create(name);
    }

    @PutMapping("{id}")
    public Header<SiteCategoryApiResponse> update(@PathVariable Long id, @RequestParam String name) {
        return siteCategoryService.update(id, name);
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return siteCategoryService.delete(id);
    }
}
