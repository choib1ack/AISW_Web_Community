package com.aisw.community.controller.api.admin;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.admin.Banner;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.BannerApiRequest;
import com.aisw.community.model.network.response.admin.BannerApiResponse;
import com.aisw.community.service.admin.BannerApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/banner")
public class BannerApiController extends CrudController<BannerApiRequest, BannerApiResponse, Banner> {

    @Autowired
    private BannerApiLogicService bannerApiLogicService;

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return bannerApiLogicService.delete(id);
    }

    @GetMapping("/")
    public Header<List<BannerApiResponse>> readBanner(@PageableDefault(sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return bannerApiLogicService.readBanner(pageable);
    }
}
