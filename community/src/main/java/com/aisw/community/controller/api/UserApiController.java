package com.aisw.community.controller.api;

import com.aisw.community.ifs.AuthService;
import com.aisw.community.model.LoginParam;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.UserApiRequest;
import com.aisw.community.model.network.response.UserApiResponse;
import com.aisw.community.service.AuthLogicService;
import com.aisw.community.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        String email = request.getData().getEmail();
        Integer studentId = request.getData().getStudentId();

        if(!authLogicService.emailDoubleCheck(email))
            return Header.ERROR("Email Already Exists");
        else if(!authLogicService.sidDoubleCheck(studentId))
            return Header.ERROR("Student Id Already Exists");
        else
            return authLogicService.signUpUser(request);
    }

    @GetMapping("{id}")
    public Header<UserApiResponse> read(@PathVariable Long id){
        return authLogicService.read(id);
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return authLogicService.loadUserByUsername(email);
    }
}
