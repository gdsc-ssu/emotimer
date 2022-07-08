package com.gdsc.timerservice.oauth.token;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.timerservice.config.properties.OAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoOAuthRetriever implements OAuthRetriever {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";

    private static final String PROFILE_URL = "https://kapi.kakao.com/v2/user/me";

    private final OAuthProperties oAuthProperties;


    @SneakyThrows
    @Override
    public String getToken(String code) {
        String accessTokenJsonString = restTemplate.exchange(
                TOKEN_URL,
                HttpMethod.POST,
                createRequest(code),
                String.class
        ).getBody();
        var accessTokenJsonMap = objectMapper.readValue(
                accessTokenJsonString,
                new TypeReference<Map<String, Object>>() {
                });

        return (String) accessTokenJsonMap.get("access_token");
    }

    private HttpEntity<MultiValueMap<String, String>> createRequest(String code) {
        var headers = createHttpHeaders();
        // HttpBody 생성.
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", oAuthProperties.getKakaoClientId());
        params.add("client_secret", oAuthProperties.getKakaoClientSecret());
        params.add("code", code);

        // HttpHeader 와 HttpBody 를 하나의 오브젝트에 담기
        return new HttpEntity<>(params, headers);

    }

    @SneakyThrows
    @Override
    public String getProfile(String accessToken) {
        ResponseEntity<String> profileResponse = restTemplate.exchange(
                PROFILE_URL,
                HttpMethod.POST,
                createProfileRequest(accessToken),
                String.class
        );

        var profileMap = objectMapper.readValue(
                profileResponse.getBody(),
                new TypeReference<Map<String, Object>>() {
                }
        );
        Map<String, Object> kakaoAccount = (Map<String, Object>) profileMap.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }

    private HttpEntity<MultiValueMap<String, String>> createProfileRequest(String token) {
        var headers = createHttpHeaders();
        headers.setBearerAuth(token); // 인증방식은 Bearer. 이 키에 access token 을 할당.
        // Http 헤더 포함하여 하나의 Http 요청 만들기
        return new HttpEntity<>(headers);
    }

}
