package com.gdsc.timerservice.oauth.token;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.timerservice.config.properties.OAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppleOAuthRetriever implements OAuthRetriever {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final OAuthProperties oAuthProperties;

    private static final String TOKEN_URL = "https://appleid.apple.com/auth/token";

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

        return (String) accessTokenJsonMap.get("id_token");

    }

    private HttpEntity<MultiValueMap<String, String>> createRequest(String code) {
        HttpHeaders headers = createHttpHeaders();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", oAuthProperties.getAppleClientId());
        params.add("client_secret", oAuthProperties.getAppleClientSecret());
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        //TODO redirect_url 살펴보기 (Apple 문서 보기)
        return new HttpEntity<>(params, headers);
    }

    @SneakyThrows
    @Override
    public String getProfile(String token) {
        String decodedProfileJsonString = new String(Base64.getDecoder()
                .decode(token.split("\\.")[1]));

        var decodedProfileJsonMap = objectMapper.readValue(
                decodedProfileJsonString,
                new TypeReference<Map<String, Object>>() {
        });

        return (String) decodedProfileJsonMap.get("sub");
    }
}
