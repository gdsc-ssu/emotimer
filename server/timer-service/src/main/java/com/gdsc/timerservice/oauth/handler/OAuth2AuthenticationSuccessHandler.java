package com.gdsc.timerservice.oauth.handler;


import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.entity.user.UserRefreshToken;
import com.gdsc.timerservice.api.repository.user.UserRefreshTokenRepository;
import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.oauth.entity.UserPrincipal;
import com.gdsc.timerservice.oauth.token.AuthToken;
import com.gdsc.timerservice.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

// OAuth2 인증 성공시 실행되는 핸들러. jwt 토큰 반환.

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info(String.valueOf(authentication));
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()){
            log.info("응답이 이미 커밋되었음." + targetUrl + " 로 리다이렉트 불가!");
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

    /**
     *
     * @param request
     * @param response
     * @param authentication
     * @return 리다이렉트할 Url 반환, 액세스 토큰과 리프레시 토큰 생성하여 응답 헤더에 담기 수행.
     */
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        Optional<String> redirectUri = Optional.of("/"); // 리다이렉트 uri. 일단은 루트로.

        if (!redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())){ // 권한이 있어야 되는 uri 로 리다이렉트하려 한다면 예외를 던질것임.
            throw new IllegalArgumentException(redirectUri + " 는 특정 권한이 있어야 접근할 수 있음.");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl()); // 기본 리다이렉트 url 은 "/" 임.

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        log.info("현재 성공적으로 소셜 로그인된 유저의 이메일 " + userPrincipal.getUser().getEmail());

        User user = userPrincipal.getUser();

        // access token 생성
        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(user.getUserId(), user.getEmail(), new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()));

        // refresh token 생성
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(user.getUserId(), user.getEmail(), new Date(now.getTime() + refreshTokenExpiry));

        // refresh token DB 에 저장
        Optional<UserRefreshToken> userRefreshToken = userRefreshTokenRepository.findById(user.getUserId());

        if (userRefreshToken.isPresent()){ // 기존에 리프레시 토큰이 있었다면, 새롭게 생성한 refresh token 으로 덮어쓰기.
            UserRefreshToken token = userRefreshToken.get();
            token.setRefreshToken(refreshToken.getToken());
        } else { // 기존에 리프레시 토큰을 한번도 발급받지 않은 유저, 즉 현재 처음으로 소셜로그인하는 경우, refresh token 신규 저장.
            UserRefreshToken newToken = UserRefreshToken.builder()
                    .email(user.getEmail())
                    .refreshToken(refreshToken.getToken())
                    .build();
            userRefreshTokenRepository.saveAndFlush(newToken);
        }
        response.setHeader("access_token" , accessToken.getToken());
        response.setHeader("refresh_token", refreshToken.getToken());

        return targetUrl;
    }


    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response){
        super.clearAuthenticationAttributes(request);
    }

    private boolean isAuthorizedRedirectUri(String uri){
        URI clientRedirectUri = URI.create(uri);
        return appProperties.getOAuth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }



}
