package com.aisw.community.controller.user;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.user.UserApiRequest;
import com.aisw.community.model.network.request.user.VerificationApiRequest;
import com.aisw.community.model.network.response.user.AlertApiResponse;
import com.aisw.community.model.network.response.user.UserApiResponse;
import com.aisw.community.model.network.response.user.VerificationApiResponse;
import com.aisw.community.service.user.AlertApiService;
import com.aisw.community.service.user.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class UserApiController {

    @Autowired
    private UserApiService userApiService;

    @Autowired
    private AlertApiService alertApiService;

    @PostMapping("/user/signup")
    public Header<UserApiResponse> signup(@RequestBody Header<UserApiRequest> request) {
        return userApiService.signup(request);
    }

    @PostMapping("/user/verification")
    public Header<VerificationApiResponse> verification(@RequestBody Header<VerificationApiRequest> request) {
        return userApiService.verification(request);
    }

    @PutMapping("/auth/user")
    public Header<UserApiResponse> update(Authentication authentication, @RequestBody Header<UserApiRequest> request) {
        return userApiService.update(authentication, request);
    }

    @DeleteMapping("/auth/user")
    public Header delete(Authentication authentication) {
        return userApiService.delete(authentication);
    }

    @GetMapping("/auth/alert")
    public Header<List<AlertApiResponse>> readAllAlert(Authentication authentication,
                                                       @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return alertApiService.readAllAlert(authentication, pageable);
    }

    @PutMapping("/auth/alert/{id}")
    public Header<AlertApiResponse> checkAlert(Authentication authentication, @PathVariable Long id) {
        return alertApiService.checkAlert(authentication, id);
    }
}
