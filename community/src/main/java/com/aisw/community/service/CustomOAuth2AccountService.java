package com.aisw.community.service;

import com.aisw.community.model.entity.user.Account;
import com.aisw.community.model.entity.SessionUser;
import com.aisw.community.model.network.request.OAuthAttributes;
import com.aisw.community.repository.user.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;


@RequiredArgsConstructor
@Service
public class CustomOAuth2AccountService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final AccountRepository accountRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Account user = saveOrUpdate(attributes);
        httpSession.setAttribute("account", new SessionUser(user)); // sessionUser 는 세션에 사용자 정보를 저장하기 위한 dto 클래스이다.
        System.out.println(user);
        String[] Name_Dep = user.getName().split("/");
        System.out.println(Name_Dep[0]);
        System.out.println(Name_Dep[1]);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getUserRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());

    }

    private Account saveOrUpdate(OAuthAttributes attributes) {
        Account user = accountRepository.findByEmail(attributes.getEmail()).map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return accountRepository.save(user);
    }
}