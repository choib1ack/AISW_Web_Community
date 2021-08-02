//package com.aisw.community.service.user;
//
//import com.aisw.community.model.entity.user.GoogleOauth;
//import com.aisw.community.model.entity.user.OAuthToken;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Service
//@RequiredArgsConstructor
//public class OauthService {
//
//    private final GoogleOauth googleOauth;
//    private final HttpServletResponse response;
//
//    public void request() {
//        String redirectURL = googleOauth.getOauthRedirectURL();
//        System.out.println();
//        System.out.println(redirectURL);
//        System.out.println();
//        try {
//            response.sendRedirect(redirectURL);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public OAuthToken requestAccessToken(String code) {
////        String token = googleOauth.requestAccessToken(code);
////        Cookie cookie = new Cookie("token",token);
////
////        response.addCookie(cookie);
////        return token;
//        return googleOauth.requestAccessToken(code);
//    }
//
//    public ResponseEntity<String> getUserInfo(String token){
//        return googleOauth.createGetRequest(token);
//    }
//
//
//}
