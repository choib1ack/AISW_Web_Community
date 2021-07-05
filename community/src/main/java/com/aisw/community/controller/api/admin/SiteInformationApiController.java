package com.aisw.community.controller.api.admin;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.SiteInformationApiRequest;
import com.aisw.community.model.network.response.admin.SiteInformationApiResponse;
import com.aisw.community.model.network.response.admin.SiteInformationApiResponseDTO;
import com.aisw.community.service.admin.SiteInformationApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/site")
public class SiteInformationApiController {

    @Autowired
    private SiteInformationApiLogicService siteInformationApiLogicService;

    @PostMapping("")
    public Header<SiteInformationApiResponse> create(@RequestBody Header<SiteInformationApiRequest> request) {
        return siteInformationApiLogicService.create(request);
    }

    @GetMapping("")
    public Header<List<SiteInformationApiResponseDTO>> readAll() {
        return siteInformationApiLogicService.readAll();
    }

    @PutMapping("")
    public Header<SiteInformationApiResponse> update(@RequestBody Header<SiteInformationApiRequest> request) {
        return siteInformationApiLogicService.update(request);
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return siteInformationApiLogicService.delete(id);
    }
}
