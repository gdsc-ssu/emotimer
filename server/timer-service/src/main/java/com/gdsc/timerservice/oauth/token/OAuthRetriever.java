package com.gdsc.timerservice.oauth.token;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public interface OAuthRetriever {


    default String getUserInfo(String code) {
        String accessToken = getToken(code);
        return getProfile(accessToken);
    }

    String getProfile(String token);

    String getToken(String code);


    default HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
}
