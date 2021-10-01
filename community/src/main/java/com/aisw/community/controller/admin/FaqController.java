package com.aisw.community.controller.admin;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.FaqApiRequest;
import com.aisw.community.model.network.request.admin.FileUploadToBannerRequest;
import com.aisw.community.model.network.response.admin.BannerApiResponse;
import com.aisw.community.model.network.response.admin.FaqApiResponse;
import com.aisw.community.service.admin.FaqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class FaqController {

    @Autowired
    private FaqService faqService;

    @PostMapping("/auth-admin/faq")
    public Header<FaqApiResponse> create(@RequestBody Header<FaqApiRequest> request) {
        return faqService.create(request.getData());
    }

    @GetMapping("/faq")
    public Header<List<FaqApiResponse>> readAll() {
        return faqService.readAll();
    }

    @PutMapping("/auth-admin/faq")
    public Header<FaqApiResponse> update(@RequestBody Header<FaqApiRequest> request) {
        return faqService.update(request.getData());
    }

    @DeleteMapping("/auth-admin/faq/{id}")
    public Header delete(@PathVariable Long id) {
        return faqService.delete(id);
    }
}
