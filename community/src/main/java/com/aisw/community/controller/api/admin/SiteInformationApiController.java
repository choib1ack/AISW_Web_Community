package com.aisw.community.controller.api.admin;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.admin.SiteInformation;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.SiteInformationApiRequest;
import com.aisw.community.model.network.response.admin.SiteInformationApiResponse;
import com.aisw.community.service.admin.SiteInformationApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cite")
public class SiteInformationApiController extends CrudController<SiteInformationApiRequest, SiteInformationApiResponse, SiteInformation> {

    @Autowired
    private SiteInformationApiLogicService siteInformationApiLogicService;

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return siteInformationApiLogicService.delete(id);
    }

    @GetMapping("/")
    public Header<List<SiteInformationApiResponse>> readSite() {
        return siteInformationApiLogicService.readCite();
    }
}
