package com.gdsc.timerservice.oauth.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.entity.user.UserRefreshToken;
import com.gdsc.timerservice.api.repository.user.UserRefreshTokenRepository;
import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.oauth.entity.UserPrincipal;
import com.gdsc.timerservice.oauth.model.TokenResponse;
import com.gdsc.timerservice.oauth.token.AuthToken;
import com.gdsc.timerservice.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

// OAuth2 인증 성공시 실행되는 핸들러. jwt 토큰 반환.

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;
    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info(String.valueOf(authentication));
        setToken(response, authentication);
    }

    /**
     * @param response
     * @param authentication
     * @return 리다이렉트할 Url 반환, 액세스 토큰과 리프레시 토큰 생성하여 응답 헤더에 담기 수행.
     */


    private void setToken(HttpServletResponse response, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        log.info("현재 성공적으로 소셜 로그인된 유저의 이메일 " + userPrincipal.getUser().getEmail());

        User user = userPrincipal.getUser();

        // access token 생성
        Date now = new Date();
        Date accessExpiry = new Date(now.getTime() + appProperties.getAuth().getTokenExpiry());
        AuthToken accessToken = tokenProvider.createAuthToken(user.getUuid(), user.getEmail(), accessExpiry);

        // refresh token 생성
        Date refreshExpiry = new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry());
        AuthToken refreshToken = tokenProvider.createAuthToken(user.getUuid(), user.getEmail(), refreshExpiry);

        // refresh token DB 에 저장
        Optional<UserRefreshToken> userRefreshToken = userRefreshTokenRepository.findById(user.getUserId());

        if (userRefreshToken.isPresent()) { // 기존에 리프레시 토큰이 있었다면, 새롭게 생성한 refresh token 으로 덮어쓰기.
            UserRefreshToken token = userRefreshToken.get();
            token.setRefreshToken(refreshToken.getToken());
        } else { // 기존에 리프레시 토큰을 한번도 발급받지 않은 유저, 즉 현재 처음으로 소셜로그인하는 경우, refresh token 신규 저장.
            UserRefreshToken newToken = UserRefreshToken.builder()
                    .uuid(user.getUuid())
                    .email(user.getEmail())
                    .refreshToken(refreshToken.getToken())
                    .build();
            userRefreshTokenRepository.save(newToken);
        }

        response.setStatus(HttpServletResponse.SC_OK); // 200
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        TokenResponse tokenResponse = new TokenResponse(accessToken.getToken(), refreshToken.getToken());
        try {
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(tokenResponse));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
