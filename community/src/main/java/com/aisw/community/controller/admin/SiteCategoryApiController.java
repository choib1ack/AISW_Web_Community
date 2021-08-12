package com.aisw.community.controller.admin;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.admin.SiteCategoryApiResponse;
import com.aisw.community.service.admin.SiteCategoryApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth-admin/site/category")
public class SiteCategoryApiController {

    @Autowired
    private SiteCategoryApiLogicService siteCategoryApiLogicService;

    @PostMapping("")
    public Header<SiteCategoryApiResponse> create(@RequestParam String name) {
        return siteCategoryApiLogicService.create(name);
    }

    @PutMapping("{id}")
    public Header<SiteCategoryApiResponse> update(@PathVariable Long id, @RequestParam String name) {
        return siteCategoryApiLogicService.update(id, name);
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return siteCategoryApiLogicService.delete(id);
    }
}
