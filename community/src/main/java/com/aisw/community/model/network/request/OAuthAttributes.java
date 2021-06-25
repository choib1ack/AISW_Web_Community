package com.aisw.community.model.network.request;

import com.aisw.community.model.entity.user.Account;
import com.aisw.community.model.enumclass.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    // User entity를 생성한다.
    // 처음 가입을 할때
    // 가입할때 기본권한을 GUEST 로 줌
    public Account toEntity() {
        return Account.builder().
                name(name)
                .email(email)
                .picture(picture)
                .role(UserRole.ADMIN)
                .build();
    }

    // OAuth2User 에서 반환하는 사용자 정보가 Map 이기 때문에 하나씩 값을 변환해줌
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


}