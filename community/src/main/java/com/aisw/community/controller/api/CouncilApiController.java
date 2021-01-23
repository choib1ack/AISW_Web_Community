package com.aisw.community.controller.api;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.CouncilApiRequest;
import com.aisw.community.model.network.request.DepartmentApiRequest;
import com.aisw.community.model.network.response.CouncilApiResponse;
import com.aisw.community.model.network.response.DepartmentApiResponse;
import com.aisw.community.service.CouncilApiLogicService;
import com.aisw.community.service.DepartmentApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice/council")
public class CouncilApiController implements CrudInterface<CouncilApiRequest, CouncilApiResponse> {

    @Autowired
    private CouncilApiLogicService councilApiLogicService;

    @Override
    @PostMapping("")
    public Header<CouncilApiResponse> create(@RequestBody Header<CouncilApiRequest> request) {
        return councilApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<CouncilApiResponse> read(@PathVariable Long id) {
        return councilApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<CouncilApiResponse> update(@RequestBody Header<CouncilApiRequest> request) {
        return councilApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return councilApiLogicService.delete(id);
    }
}
