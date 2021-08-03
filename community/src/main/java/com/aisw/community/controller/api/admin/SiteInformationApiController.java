package com.aisw.community.controller.api.admin;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.FileUploadToSiteInformationDTO;
import com.aisw.community.model.network.response.admin.SiteCategoryApiResponse;
import com.aisw.community.model.network.response.admin.SiteInformationApiResponse;
import com.aisw.community.model.network.response.admin.SiteInformationWithFileApiResponse;
import com.aisw.community.service.admin.SiteCategoryApiLogicService;
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

    @PostMapping("/auth-admin")
    public Header<SiteInformationApiResponse> create(@ModelAttribute FileUploadToSiteInformationDTO request) {
        return siteInformationApiLogicService.create(request);
    }

    @GetMapping("/site")
    public Header<List<SiteInformationWithFileApiResponse>> read() {
        return siteInformationApiLogicService.read();
    }

    @PutMapping("/auth-admin")
    public Header<SiteInformationApiResponse> update(@ModelAttribute FileUploadToSiteInformationDTO request) {
        return siteInformationApiLogicService.update(request);
    }

    @DeleteMapping("/auth-admin/{id}")
    public Header delete(@PathVariable Long id) {
        return siteInformationApiLogicService.delete(id);
    }
}
