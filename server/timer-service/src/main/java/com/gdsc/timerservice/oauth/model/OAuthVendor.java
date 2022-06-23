package com.gdsc.timerservice.oauth.model;

import com.gdsc.timerservice.oauth.entity.ProviderType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public enum OAuthVendor {
    KAKAO {
        @Override
        public String getTokenUrl() {
            return "https://kauth.kakao.com/oauth/token";
        }

        @Override
        public String getProfileUrl() {
            return "https://kapi.kakao.com/v2/user/me";
        }

        @Override
        public <T extends AbstractOAuthToken> Class<T> getTokenClass() {
            //TODO 여기가 좀 마음에 걸림. 잘 작동하는지 확인해 볼 것.
            return (Class<T>) KakaoOAuthToken.class;
        }

        @Override
        public <T extends AbstractProfile> Class<T> getProfileClass() {
            return (Class<T>) KakaoProfile.class;
        }


        @Override
        public HttpEntity<MultiValueMap<String, String>> createAccessTokenRequest(String code) {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            // HttpBody 생성.
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", "116cc9d3778c5d9578487bce8f08e024");
            // TODO 클라이언트 id 와 secret .proterties 파일에서 불러오기
            params.add("client_secret", "aTnSYwnw2qCJ0zZ9Br74HUUoWS0kMVwy");
            params.add("redirect_uri", "http://localhost:8080/callback/kakao");
            params.add("code", code);

            // HttpHeader 와 HttpBody 를 하나의 오브젝트에 담기
            return new HttpEntity<>(params, header);
        }

        @Override
        public HttpEntity<MultiValueMap<String, String>> createProfileRequest(String accessToken) {
            HttpHeaders header2 = new HttpHeaders();
            header2.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            header2.setBearerAuth(accessToken); // 인증방식은 Bearer. 이 키에 access token 을 할당.
            // Http 헤더 포함하여 하나의 Http 요청 만들기
            return new HttpEntity<>(header2);
        }

        @Override
        public ProviderType getProviderType() {
            return ProviderType.KAKAO;
        }
    };

    /**
     * TODO 주석 달기
     *
     * @return
     */
    public abstract String getTokenUrl();

    public abstract String getProfileUrl();

    public abstract <T extends AbstractOAuthToken> Class<T> getTokenClass();

    public abstract <T extends AbstractProfile> Class<T> getProfileClass();

    /**
     * TODO 주석 달기
     *
     * @param code
     * @return
     */
    public abstract HttpEntity<MultiValueMap<String, String>> createAccessTokenRequest(String code);

    public abstract HttpEntity<MultiValueMap<String, String>> createProfileRequest(String accessToken);

    public abstract ProviderType getProviderType();


    public static OAuthVendor getVendor(String name) {
        return valueOf(name.toUpperCase());
    }
}
