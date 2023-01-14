package com.gdsc.timerservice.util;

import javax.servlet.http.HttpServletRequest;

public class HeaderUtil {
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request){
        String headerValue = request.getHeader(HEADER_AUTHORIZATION); // 요청헤더에서 Authorization 키값을 가져옴. jwt access token 이 들어있음.
        if (headerValue == null){
            return null;
        }

        if (headerValue.startsWith(TOKEN_PREFIX)){ // access token 의 값이 "Beareer " 로 시작한다면
            return headerValue.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

    public static String getRefreshToken(HttpServletRequest request){
        return request.getHeader("refresh_token");
    }
}
