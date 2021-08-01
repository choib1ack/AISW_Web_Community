package com.aisw.community.controller.api.user;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.user.AccountApiRequest;
import com.aisw.community.model.network.response.user.AccountApiResponse;
import com.aisw.community.service.user.AccountApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user2")
public class User2ApiController {

    @Autowired
    private AccountApiService accountApiService;

    @PostMapping("/signup")
    public Header<AccountApiResponse> signup(@RequestBody Header<AccountApiRequest> request) {
        return accountApiService.signup(request);
    }
}
