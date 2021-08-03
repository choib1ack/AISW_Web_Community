package com.aisw.community.controller.api.admin;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.FileUploadToBannerDTO;
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
@RequestMapping("/auth-admin/banner")
public class BannerApiController {

    @Autowired
    private BannerApiLogicService bannerApiLogicService;

    @PostMapping("")
    public Header<BannerApiResponse> create(@ModelAttribute FileUploadToBannerDTO request) {
        return bannerApiLogicService.create(request);
    }

    @GetMapping("")
    public Header<List<BannerApiResponse>> readAll(@PageableDefault(sort = {"startDate", "endDate"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return bannerApiLogicService.read(pageable);
    }

    @PutMapping("")
    public Header<BannerApiResponse> update(@ModelAttribute FileUploadToBannerDTO request) {
        return bannerApiLogicService.update(request);
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return bannerApiLogicService.delete(id);
    }
}
