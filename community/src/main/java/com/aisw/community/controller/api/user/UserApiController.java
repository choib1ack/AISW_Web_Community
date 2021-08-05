package com.aisw.community.controller.api.user;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.user.UserApiRequest;
import com.aisw.community.model.network.request.user.VerificationApiRequest;
import com.aisw.community.model.network.response.user.UserApiResponse;
import com.aisw.community.model.network.response.user.VerificationApiResponse;
import com.aisw.community.service.user.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
class UserApiController {

    @Autowired
    private UserApiService userApiService;

    @PostMapping("/signup")
    public Header<UserApiResponse> signup(@RequestBody Header<UserApiRequest> request) {
        System.out.println(request);
        return userApiService.signup(request);
    }

    @PostMapping("/verification")
    public Header<VerificationApiResponse> verification(@RequestBody Header<VerificationApiRequest> request) {
        return userApiService.verification(request);
    }
}
