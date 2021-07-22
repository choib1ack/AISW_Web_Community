package com.aisw.community.controller.api.user;

import com.aisw.community.model.entity.user.OAuthToken;
import com.aisw.community.service.user.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class OauthController {

    private final OauthService oauthService;
    private final HttpServletResponse response;

    @GetMapping(value = "")
    public void socialLoginType() {
        oauthService.request();

    }

    @GetMapping(value = "/google/callback")
    public ResponseEntity<String> callback(@RequestParam(name = "code") String code) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        OAuthToken oAuthToken = oauthService.requestAccessToken(code);
        Cookie cookie = new Cookie("token", oAuthToken.getIdToken());
        cookie.setPath("/");
        cookie.setMaxAge(60);

        response.addCookie(cookie);
        return oauthService.getUserInfo(oAuthToken.getAccessToken());
    }

    @GetMapping(value = "/signup")
    public ResponseEntity<String> signup(@CookieValue(name = "token") Cookie token) {
        System.out.println(token.getValue());
        return oauthService.getUserInfo(token.getValue());
    }
}
