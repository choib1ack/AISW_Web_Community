package com.aisw.community.controller.api.admin;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.admin.CiteInformation;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.CiteInformationApiRequest;
import com.aisw.community.model.network.response.admin.BannerApiResponse;
import com.aisw.community.model.network.response.admin.CiteInformationApiResponse;
import com.aisw.community.service.admin.CiteInformationApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cite")
public class CiteInformationApiController extends CrudController<CiteInformationApiRequest, CiteInformationApiResponse, CiteInformation> {

    @Autowired
    private CiteInformationApiLogicService citeInformationApiLogicService;

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return citeInformationApiLogicService.delete(id);
    }

    @GetMapping("")
    public Header<List<CiteInformationApiResponse>> readCite() {
        return citeInformationApiLogicService.readCite();
    }
}
