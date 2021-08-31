package com.aisw.community.controller.user;

import com.aisw.community.config.auth.PrincipalDetails;
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
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return userService.update(principal.getUser(), request.getData());
    }

    @DeleteMapping("/auth/user")
    public Header delete(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return userService.delete(principal.getUser());
    }

    @GetMapping("/auth/alert")
    public Header<List<AlertApiResponse>> readAllAlert(Authentication authentication,
                                                       @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return alertService.readAllAlert(principal.getUser(), pageable);
    }

    @GetMapping("/auth/alert/{id}")
    public Long checkAlert(Authentication authentication, @PathVariable Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        alertService.checkAlert(principal.getUser(), id);
        return alertService.getNumberOfUnreadAlert(principal.getUser().getId());
    }

    @GetMapping("/auth/refresh")
    public Header getRefreshToken() {
        return Header.OK();
    }
}
