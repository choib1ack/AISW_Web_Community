package com.aisw.community.controller.user;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.user.UserApiRequest;
import com.aisw.community.model.network.request.user.VerificationRequest;
import com.aisw.community.model.network.response.user.AlertApiResponse;
import com.aisw.community.model.network.response.user.UserApiResponse;
import com.aisw.community.model.network.response.user.VerificationApiResponse;
import com.aisw.community.service.user.AlertService;
import com.aisw.community.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AlertService alertService;

    @PostMapping("/user/signup")
    public Header<UserApiResponse> signup(@RequestBody Header<UserApiRequest> request) {
        return userService.signup(request.getData());
    }

    @PostMapping("/user/verification")
    public Header<VerificationApiResponse> verification(@RequestBody Header<VerificationRequest> request) {
        return userService.verification(request.getData());
    }

    @PutMapping("/auth/user")
    public Header<UserApiResponse> update(Authentication authentication, @RequestBody Header<UserApiRequest> request) {
        return userService.update(authentication, request.getData());
    }

    @DeleteMapping("/auth/user")
    public Header delete(Authentication authentication) {
        return userService.delete(authentication);
    }

    @GetMapping("/auth/alert")
    public Header<List<AlertApiResponse>> readAllAlert(Authentication authentication,
                                                       @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return alertService.readAllAlert(authentication, pageable);
    }

    @GetMapping("/auth/alert/{id}")
    public Header<AlertApiResponse> checkAlert(Authentication authentication, @PathVariable Long id) {
        return alertService.checkAlert(authentication, id);
    }

    @GetMapping("/auth/refresh")
    public Header getRefreshToken() {
        return Header.OK();
    }
}
