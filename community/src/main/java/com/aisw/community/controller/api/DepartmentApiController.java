package com.aisw.community.controller.api;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.DepartmentApiRequest;
import com.aisw.community.model.network.response.DepartmentApiResponse;
import com.aisw.community.service.DepartmentApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice/department")
public class DepartmentApiController implements CrudInterface<DepartmentApiRequest, DepartmentApiResponse> {

    @Autowired
    private DepartmentApiLogicService departmentApiLogicService;

    @Override
    @PostMapping("")
    public Header<DepartmentApiResponse> create(@RequestBody Header<DepartmentApiRequest> request) {
        return departmentApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<DepartmentApiResponse> read(@PathVariable Long id) {
        return departmentApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<DepartmentApiResponse> update(@RequestBody Header<DepartmentApiRequest> request) {
        return departmentApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return departmentApiLogicService.delete(id);
    }
}
