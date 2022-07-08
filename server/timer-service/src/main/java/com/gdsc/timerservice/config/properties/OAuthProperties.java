package com.gdsc.timerservice.config.properties;

import com.gdsc.timerservice.oauth.model.OAuthVendor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "oauth")
@PropertySource(value = "classpath:application-oauth.properties")
@RequiredArgsConstructor
public class OAuthProperties {
    private String kakaoClientId;
    private String kakaoClientSecret;

    @Component
    @RequiredArgsConstructor
    static class OAuthPropertiesRunner implements ApplicationRunner {
        private final OAuthProperties oAuthProperties;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            OAuthVendor.oAuthProperties = oAuthProperties;
        }
    }
}
