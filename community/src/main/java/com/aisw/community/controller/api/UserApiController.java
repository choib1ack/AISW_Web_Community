package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.ifs.AuthService;
import com.aisw.community.model.LoginParam;
import com.aisw.community.model.entity.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.UserApiRequest;
import com.aisw.community.model.network.response.UserApiResponse;
import com.aisw.community.service.AuthLogicService;
import com.aisw.community.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserApiController implements AuthService {

    @Autowired
    AuthLogicService authLogicService;

    @Autowired
    UserApiLogicService userApiLogicService;

    @Override
    @PostMapping("/signup")
    public Header<UserApiResponse> signUpUser(@RequestBody Header<UserApiRequest> request) {
        return authLogicService.signUpUser(request);
    }

    @Override
    @GetMapping("/login")
    public Header<UserApiResponse> loginUser(@RequestBody Header<LoginParam> loginParam) {
        return authLogicService.loginUser(loginParam);
    }

    @PostMapping("")
    public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> request){
        return userApiLogicService.create(request);
    }
}
