package com.aisw.community.controller.admin;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.FileUploadToSiteInformationDTO;
import com.aisw.community.model.network.response.admin.SiteInformationApiResponse;
import com.aisw.community.model.network.response.admin.SiteInformationWithFileApiResponse;
import com.aisw.community.service.admin.SiteInformationApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping()
public class SiteInformationApiController {

    @Autowired
    private SiteInformationApiLogicService siteInformationApiLogicService;

    @PostMapping("/auth-admin/site")
    public Header<SiteInformationApiResponse> create(@ModelAttribute FileUploadToSiteInformationDTO request) {
        return siteInformationApiLogicService.create(request);
    }

    @GetMapping("auth-admin/site")
    public Header<List<SiteInformationWithFileApiResponse>> readAll() {
        return siteInformationApiLogicService.readAll();
    }

    @PutMapping("/auth-admin/site")
    public Header<SiteInformationApiResponse> update(@ModelAttribute FileUploadToSiteInformationDTO request) {
        return siteInformationApiLogicService.update(request);
    }

    @DeleteMapping("/auth-admin/site/{id}")
    public Header delete(@PathVariable Long id) {
        return siteInformationApiLogicService.delete(id);
    }
}
