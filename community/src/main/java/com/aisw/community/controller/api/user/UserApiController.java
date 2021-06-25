package com.aisw.community.controller.api.user;

import com.aisw.community.ifs.AuthService;
import com.aisw.community.model.LoginParam;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.user.AccountApiRequest;
import com.aisw.community.model.network.response.user.AccountApiResponse;
import com.aisw.community.service.user.AuthLogicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@Api(value = "UserController", tags = "User Controller")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserApiController {

    @Autowired
    AuthLogicService authLogicService;


    //    @Override
    @ApiOperation("User Info Register")
    @PostMapping("/signup")
    public Header<AccountApiResponse> signUpUser(@ApiParam(value = "User Sign up", required = true) @RequestBody Header<AccountApiRequest> request) {
        return authLogicService.signUpUser(request);
    }

    @ApiOperation("User Info Search")
    @GetMapping("{id}")
    public Header<AccountApiResponse> read(@ApiParam(value = "User Info", required = true) @PathVariable(value = "id") final Long id) {
        return authLogicService.read(id);
    }

    @PostMapping("")
    public Header<AccountApiResponse> create(@RequestBody Header<AccountApiRequest> request) {
        return authLogicService.create(request);
    }

    @ApiOperation("User Login")
    @RequestMapping("/login/{email}")
    public Header<AccountApiResponse> loginUser(@PathVariable String email) {
        return authLogicService.loginUser(email);
    }

    @PutMapping("/update")
    public Header<AccountApiResponse> update(@RequestBody Header<AccountApiRequest> request) {
        return authLogicService.update(request);
    }

//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return authLogicService.loadUserByUsername(email);
//    }
}
