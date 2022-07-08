package com.gdsc.timerservice.oauth.entity;

public enum ProviderType {
    GOOGLE,
    KAKAO,
    NAVER,
    APPLE;

    public static ProviderType get(String name) {
        return ProviderType.valueOf(name.toUpperCase());
    }



}
