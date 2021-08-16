package com.aisw.community.controller;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.HomeApiResponse;
import com.aisw.community.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("")
    public Header<HomeApiResponse> main() {
        return homeService.main();
    }
}
