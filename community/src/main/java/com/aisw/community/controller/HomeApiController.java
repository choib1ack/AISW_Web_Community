package com.aisw.community.controller;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.HomeApiResponse;
import com.aisw.community.service.HomeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeApiController {

    @Autowired
    private HomeApiService homeApiService;

    @GetMapping("")
    public Header<HomeApiResponse> main() {
        return homeApiService.main();
    }
}
