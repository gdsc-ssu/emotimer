package com.gdsc.timerservice.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@ConfigurationProperties(prefix = "oauth")
@PropertySource(value = "classpath:application-oauth.properties")
@RequiredArgsConstructor
public class OAuthProperties {
    private String kakaoClientId;
    private String kakaoClientSecret;

    private String appleClientId;
    private String appleClientSecret;

}
