package com.gdsc.timerservice.oauth.filter;

import com.gdsc.timerservice.oauth.service.IssueRefreshService;
import com.gdsc.timerservice.oauth.token.AuthToken;
import com.gdsc.timerservice.oauth.token.AuthTokenProvider;
import com.gdsc.timerservice.util.HeaderUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TokenAuthenticationFilter 는 OncePerRequestFilter 클래스를 상속받았기 때문에 "매 요청마다 불려질것" 임.
 * 이 필터에서는 요청헤더에서 토큰을 꺼내 유효성 검사를 진행한다.
 *
 * 토큰이 유효하다면 SecurityContextHolder 에 인증객체를 저장하고,
 * 토큰이 유효하지 않다면 그냥 이후 필터를 타게 한다.
 */
@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenProvider tokenProvider;
    private final IssueRefreshService issueRefreshService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // 액세스 토큰
        String tokenStr = HeaderUtil.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);

        // 리프레시 토큰
        String refreshTokenStr = HeaderUtil.getAccessToken(request);
        AuthToken refreshToken = tokenProvider.convertAuthToken(refreshTokenStr);

        // 토큰이 유효하다면 시큐리티 컨텍스트 홀더에 현재 인증객체 저장.
        try {
            if (token.validate()) {
                setAuthentication(token);
            }
        } catch (ExpiredJwtException e) {
            log.info("토큰 만료됨");
            issueRefreshService.refreshToken(request, response);
            // throw new AccessTokenExpiredException(); // 예외를 여기서 터뜨리면, 사용자 화면에 바로 에러 스택이 보이게 됨.
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(AuthToken token) {
        log.info("유효한 액세스 토큰이 담긴 요청을 하셨습니다.");
        Authentication authentication = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
