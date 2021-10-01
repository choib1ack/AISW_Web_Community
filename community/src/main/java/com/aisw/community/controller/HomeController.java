package com.aisw.community.controller;

import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.HomeApiResponse;
import com.aisw.community.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/home")
    public Header<HomeApiResponse> main() {
        return homeService.main();
    }

    @GetMapping("/auth/home")
    public Header<HomeApiResponse> mainForUser(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return homeService.mainForUser(principal.getUser());
    }
}
