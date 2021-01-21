package com.aisw.community.controller;

import com.aisw.community.model.network.Header;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class GetController {

    // { "resultCode" : "OK", "description" : "OK"
    @GetMapping("/header")
    public Header getHeader(){
        return Header.builder().resultCode("OK").description("OK").build();
    }
}
