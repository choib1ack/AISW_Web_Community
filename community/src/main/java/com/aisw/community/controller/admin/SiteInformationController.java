package com.aisw.community.controller.admin;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.FileUploadToSiteRequest;
import com.aisw.community.model.network.response.admin.SiteInformationApiResponse;
import com.aisw.community.model.network.response.admin.SiteInformationWithFileApiResponse;
import com.aisw.community.service.admin.SiteInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth-admin/site")
public class SiteInformationController {

    @Autowired
    private SiteInformationService siteInformationService;

    @PostMapping("")
    public Header<SiteInformationApiResponse> create(@ModelAttribute FileUploadToSiteRequest request) {
        return siteInformationService.create(request.getSiteInformationApiRequest(), request.getFiles());
    }

    @GetMapping("")
    public Header<List<SiteInformationWithFileApiResponse>> readAll() {
        return siteInformationService.readAll();
    }

    @PutMapping("")
    public Header<SiteInformationApiResponse> update(@ModelAttribute FileUploadToSiteRequest request) {
        return siteInformationService.update(request.getSiteInformationApiRequest(), request.getFiles());
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return siteInformationService.delete(id);
    }
}
