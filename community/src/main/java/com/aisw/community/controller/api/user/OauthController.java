package com.aisw.community.controller.api.user;

import com.aisw.community.service.user.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class OauthController {

    private final OauthService oauthService;

    @GetMapping(value = "/")
    public void socialLoginType() {
        oauthService.request();

    }

    @GetMapping(value = "/google/callback")
    public String callback(@RequestParam(name = "code") String code) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        return oauthService.requestAccessToken(code);
    }
}
