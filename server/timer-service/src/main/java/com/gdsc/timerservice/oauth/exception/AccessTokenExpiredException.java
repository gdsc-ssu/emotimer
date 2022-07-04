package com.gdsc.timerservice.oauth.exception;

public class AccessTokenExpiredException extends RuntimeException {
    public AccessTokenExpiredException(){
        super("액세스 토큰이 만료되었음. 액세스 토큰 새로 발급하는 /refresh api 를 호출해주기 바람.");
    }
}
