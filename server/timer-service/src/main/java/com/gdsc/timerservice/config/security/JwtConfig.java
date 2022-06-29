package com.gdsc.timerservice.config.security;

import com.gdsc.timerservice.oauth.token.AuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 이 클래스 안씀.
 */
@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public AuthTokenProvider jwtProvider(){
        return new AuthTokenProvider(secret);
    }
}