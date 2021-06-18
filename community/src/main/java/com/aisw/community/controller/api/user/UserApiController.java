package com.aisw.community.controller.api.user;

import com.aisw.community.ifs.AuthService;
import com.aisw.community.model.LoginParam;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.user.AccountApiRequest;
import com.aisw.community.model.network.response.user.AccountApiResponse;
import com.aisw.community.service.CustomOAuth2AccountService;
import com.aisw.community.service.user.AuthLogicService;
import com.aisw.community.service.user.UserApiLogicService;
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
public class UserApiController implements AuthService {

    @Autowired
    AuthLogicService authLogicService;

    @Autowired
    CustomOAuth2AccountService customOAuth2AccountService;

    @Autowired
    UserApiLogicService userApiLogicService;

//    @Override
    @ApiOperation("User Info Register")
    @PostMapping("/signup")
    public Header<AccountApiResponse> signUpUser(@ApiParam(value = "User Sign up", required = true) @RequestBody Header<AccountApiRequest> request) {

        System.out.println("hello");
        String email = request.getData().getEmail();
        Integer studentId = request.getData().getStudentId();

        if(!authLogicService.emailDoubleCheck(email))
            return Header.ERROR("Email Already Exists");
        else if(!authLogicService.sidDoubleCheck(studentId))
            return Header.ERROR("Student Id Already Exists");
        else
            return authLogicService.signUpUser(request);
    }

    @ApiOperation("User Info Search")
    @GetMapping("{id}")
    public Header<AccountApiResponse> read(@ApiParam(value = "User Info", required = true) @PathVariable(value = "id") final Long id){
        return authLogicService.read(id);
    }

//    @Override
    @ApiOperation("User Login")
    @RequestMapping("/login")
    public Header<AccountApiResponse> loginUser(LoginParam loginParam) {
        return authLogicService.loginUser(loginParam);
    }

    @PostMapping("")
    public Header<AccountApiResponse> create(@RequestBody Header<AccountApiRequest> request){
        return userApiLogicService.create(request);
    }

    @PutMapping("/update")
    public Header<AccountApiResponse> update(@RequestBody Header<AccountApiRequest> request){
        System.out.println(request.getData().getEmail());

        return userApiLogicService.update(request);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return authLogicService.loadUserByUsername(email);
    }
}
