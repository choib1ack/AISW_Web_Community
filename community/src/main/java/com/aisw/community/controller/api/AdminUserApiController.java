package com.aisw.community.controller.api;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.AdminUserApiRequest;
import com.aisw.community.model.network.response.AdminUserApiResponse;
import com.aisw.community.service.AdminUserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminUserApiController implements CrudInterface<AdminUserApiRequest, AdminUserApiResponse> {

    @Autowired
    private AdminUserApiLogicService adminUserApiLogicService;

    @Override
    @PostMapping("") // /admin
    public Header<AdminUserApiResponse> create(@RequestBody Header<AdminUserApiRequest> request) {
        return adminUserApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}") // /admin/{id}
    public Header<AdminUserApiResponse> read(@PathVariable(name = "id") Long id) {
        return adminUserApiLogicService.read(id);
    }

    @Override
    @PutMapping("") // /admin
    public Header<AdminUserApiResponse> update(@RequestBody Header<AdminUserApiRequest> request) {
        return adminUserApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}") // /admin/{id}
    public Header delete(@PathVariable(name = "id") Long id) {
        return adminUserApiLogicService.delete(id);
    }
}
