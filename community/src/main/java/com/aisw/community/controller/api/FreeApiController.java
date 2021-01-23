package com.aisw.community.controller.api;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.DepartmentApiRequest;
import com.aisw.community.model.network.request.FreeApiRequest;
import com.aisw.community.model.network.response.DepartmentApiResponse;
import com.aisw.community.model.network.response.FreeApiResponse;
import com.aisw.community.service.DepartmentApiLogicService;
import com.aisw.community.service.FreeApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/free")
public class FreeApiController implements CrudInterface<FreeApiRequest, FreeApiResponse> {

    @Autowired
    private FreeApiLogicService freeApiLogicService;

    @Override
    @PostMapping("")
    public Header<FreeApiResponse> create(@RequestBody Header<FreeApiRequest> request) {
        return freeApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<FreeApiResponse> read(@PathVariable Long id) {
        return freeApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<FreeApiResponse> update(@RequestBody Header<FreeApiRequest> request) {
        return freeApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return freeApiLogicService.delete(id);
    }
}
