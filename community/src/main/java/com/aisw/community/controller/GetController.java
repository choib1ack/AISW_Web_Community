package com.aisw.community.controller;


import com.aisw.community.model.LoginParam;
import com.aisw.community.model.network.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class GetController {

//    @RequestMapping(method = RequestMethod.GET, path = "/getMethod")
//    public String getRequest(){
//        return "";
//    }

    @RequestMapping("/getLoginParam")
    public String getLoginInfo(LoginParam loginParam){
        System.out.println(loginParam.getEmail());
//        System.out.println(loginParam.getPassword());


        return "OK";
    }
}
