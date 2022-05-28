package com.gdsc.timerservice.oauth.filter;

import com.gdsc.timerservice.oauth.token.AuthToken;
import com.gdsc.timerservice.oauth.token.AuthTokenProvider;
import com.gdsc.timerservice.util.HeaderUtil;
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
 * TokenAuthenticationFilter 는 OncePerRequestFilter 클래스를 상속받았기 때문에 매 요청마다 불려질것임.
 * 이 필터에서는 요청헤더에서 토큰을 꺼내 유효성 검사를 진행한다.
 *
 * 토큰이 유효하다면 SecurityContextHolder 에 인증객체를 저장하고,
 * 토큰이 유효하지 않다면 그냥 이후 필터를 타게 한다.
 */
@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String tokenStr = HeaderUtil.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);

        // 토큰이 유효하다면 시큐리티 컨텍스트 홀더에 현재 인증객체 저장.
        if (token.validate()){
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 이후 나머지 필터를 타게 하자~
        filterChain.doFilter(request, response);
    }
}
