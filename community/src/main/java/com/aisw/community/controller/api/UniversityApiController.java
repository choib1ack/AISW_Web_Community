package com.aisw.community.controller.api;

import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.NoticeApiRequest;
import com.aisw.community.model.network.request.UniversityApiRequest;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.model.network.response.UniversityApiResponse;
import com.aisw.community.service.UniversityApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice/university")
public class UniversityApiController implements CrudInterface<UniversityApiRequest, UniversityApiResponse> {

    @Autowired
    private UniversityApiLogicService universityApiLogicService;

    @Override
    @PostMapping("")
    public Header<UniversityApiResponse> create(@RequestBody Header<UniversityApiRequest> request) {
        return universityApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<UniversityApiResponse> read(@PathVariable Long id) {
        return universityApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<UniversityApiResponse> update(@RequestBody Header<UniversityApiRequest> request) {
        return universityApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return universityApiLogicService.delete(id);
    }
}
