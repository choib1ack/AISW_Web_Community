package com.aisw.community.controller.admin;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.FileUploadToBannerRequest;
import com.aisw.community.model.network.response.admin.BannerApiResponse;
import com.aisw.community.service.admin.BannerService;
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
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping("")
    public Header<BannerApiResponse> create(@ModelAttribute FileUploadToBannerRequest request) {
        return bannerService.create(request.getBannerApiRequest(), request.getFiles());
    }

    @GetMapping("")
    public Header<List<BannerApiResponse>> readAll(@PageableDefault(sort = {"startDate", "endDate"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return bannerService.readAll(pageable);
    }

    @PutMapping("")
    public Header<BannerApiResponse> update(@ModelAttribute FileUploadToBannerRequest request) {
        return bannerService.update(request.getBannerApiRequest(), request.getFiles());
    }

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return bannerService.delete(id);
    }
}
