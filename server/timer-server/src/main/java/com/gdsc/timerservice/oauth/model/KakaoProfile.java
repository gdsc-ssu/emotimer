package com.gdsc.timerservice.oauth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.gdsc.timerservice.oauth.entity.ProviderType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoProfile extends AbstractProfile {

    private Long id;
    private KakaoAccount kakaoAccount;

    @Override
    public String getEmail() {
        return this.kakaoAccount.getEmail();
    }

    @Override
    public String getUsername() {
        return this.kakaoAccount.getProfile().getNickname();
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.KAKAO;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class KakaoAccount {
        private Profile profile;
        private String email;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Profile {
        String nickname;
    }

}
