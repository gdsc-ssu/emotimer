package com.gdsc.timerservice.oauth.service;

import com.gdsc.timerservice.api.entity.user.UserRefreshToken;
import com.gdsc.timerservice.api.repository.user.UserRefreshTokenRepository;
import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.oauth.model.TokenResponse;
import com.gdsc.timerservice.oauth.token.AuthToken;
import com.gdsc.timerservice.oauth.token.AuthTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueRefreshService {
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    public TokenResponse refreshToken(String refreshToken) {
        log.info("리프레시 토큰 발급 준비중...");
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);
        Claims tokenClaims = authRefreshToken.getTokenClaims();

        if (!authRefreshToken.validate()) {
            log.info("유효하지 않은 리프레시 토큰임.");
            throw new RuntimeException("유효하지 않은 리프레시 토큰임.");
        }

        // email 과 refresh token 으로 DB 확인. 있다면 새로운 토큰 생성하여 응답
        String uuid = tokenClaims.get("id").toString();
        UserRefreshToken userRefreshToken =
                userRefreshTokenRepository.findByUuidAndRefreshToken(
                        uuid,
                        refreshToken
                );

        if (userRefreshToken == null) {
            log.info("해당하는 리프레시 토큰이 DB에 존재하지 않음");
            throw new RuntimeException("해당하는 리프레시 토큰이 DB에 존재하지 않음");
        }
        // 새로운 토큰 생성
        String email = tokenClaims.getSubject(); // 유저 이메일
        AuthToken accessToken = tokenProvider.createAuthToken(
                uuid,
                email,
                appProperties.getTokenExpiry());
        log.info("새로운 액세스 토큰 헤더에 탑재 완료!");
        return new TokenResponse(
                accessToken.getToken(),
                userRefreshToken.getRefreshToken()
        );

    }

}
