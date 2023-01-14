package com.gdsc.timerservice.oauth.info.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gdsc.timerservice.oauth.info.OAuth2UserInfo;
import lombok.NoArgsConstructor;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        this.attributes = (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getEmail() {
        System.out.println("애트리뷰트 출력: " + attributes);
        return (String) attributes.get("email");
    }

    @Override
    public String getUserName() {
        return (String) attributes.get("nickname");
    }

    @Override
    public String getImageUrl() {
        return null;
    }
}
