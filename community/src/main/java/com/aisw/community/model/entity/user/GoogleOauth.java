//package com.aisw.community.model.entity.user;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//public class GoogleOauth {
//
//    @Value("${sns.google.url}")
//    private String GOOGLE_SNS_BASE_URL;
//    @Value("${sns.google.client.id}")
//    private String GOOGLE_SNS_CLIENT_ID;
//    @Value("${sns.google.callback.url}")
//    private String GOOGLE_SNS_CALLBACK_URL;
//    @Value("${sns.google.client.secret}")
//    private String GOOGLE_SNS_CLIENT_SECRET;
//    @Value("${sns.google.token.url}")
//    private String GOOGLE_SNS_TOKEN_BASE_URL;
//    @Value("${sns.google.client.info}")
//    private String GOOGLE_SNS_CLIENT_INFO;
//
//    private final ObjectMapper objectMapper;
//
//    public String getOauthRedirectURL() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("scope", "email+profile");
//        params.put("response_type", "code");
//        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
//        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
//
//        String parameterString = params.entrySet().stream()
//                .map(x -> x.getKey() + "=" + x.getValue())
//                .collect(Collectors.joining("&"));
//
//        return GOOGLE_SNS_BASE_URL + "?" + parameterString;
//    }
//
//    public OAuthToken requestAccessToken(String code) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("code", code);
//        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
//        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
//        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
//        params.put("grant_type", "authorization_code");
//        params.put("access_type", "offline");
//
//        ResponseEntity<String> responseEntity =
//                restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);
//
//
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            OAuthToken token = getAccessToken(responseEntity);
//            System.out.println(responseEntity.getBody());
//            System.out.println(token.getIdToken());
//            return token;
//        }
//        throw new IllegalArgumentException("구글 로그인 요청 실패");
//    }
//
//    public OAuthToken getAccessToken(ResponseEntity<String> response) {
//        OAuthToken oAuthToken = null;
//        try {
//            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return oAuthToken;
//    }
//
//    public ResponseEntity<String> createGetRequest(String oAuthToken) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + oAuthToken);
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
//
//        return restTemplate.exchange(GOOGLE_SNS_CLIENT_INFO, HttpMethod.GET, request, String.class);
//    }
//
//
//    public String requestAccessTokenUsingURL(String code) {
//        try {
//            URL url = new URL(GOOGLE_SNS_TOKEN_BASE_URL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setDoOutput(true);
//
//            Map<String, Object> params = new HashMap<>();
//            params.put("code", code);
//            params.put("client_id", GOOGLE_SNS_CLIENT_ID);
//            params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
//            params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
//            params.put("grant_type", "authorization_code");
//
//            String parameterString = params.entrySet().stream()
//                    .map(x -> x.getKey() + "=" + x.getValue())
//                    .collect(Collectors.joining("&"));
//
//            BufferedOutputStream bous = new BufferedOutputStream(conn.getOutputStream());
//            bous.write(parameterString.getBytes());
//            bous.flush();
//            bous.close();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            StringBuilder sb = new StringBuilder();
//            String line;
//
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//
//            if (conn.getResponseCode() == 200) {
//                return sb.toString();
//            }
//            return "구글 로그인 요청 처리 실패";
//        } catch (IOException e) {
//            throw new IllegalArgumentException("알 수 없는 구글 로그인 Access Token 요청 URL 입니다 :: " + GOOGLE_SNS_TOKEN_BASE_URL);
//        }
//    }
//}