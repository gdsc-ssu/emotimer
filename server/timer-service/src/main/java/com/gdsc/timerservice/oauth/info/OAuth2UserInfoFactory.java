package com.gdsc.timerservice.oauth.info;

import com.gdsc.timerservice.oauth.entity.ProviderType;
import com.gdsc.timerservice.oauth.info.impl.GoogleOAuth2UserInfo;
//import com.gdsc.timerservice.oauth.info.impl.KakaoOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes){
        switch (providerType){
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
//            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("이 소셜 로그인은 아직 지원하지 않습니다... ㅈㅅㅈㅅ...");
        }
    }
}
