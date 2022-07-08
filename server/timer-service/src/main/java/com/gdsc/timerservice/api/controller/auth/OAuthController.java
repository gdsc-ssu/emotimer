package com.gdsc.timerservice.api.controller.auth;

import com.gdsc.timerservice.api.service.auth.OAuth2Service;
import com.gdsc.timerservice.oauth.exception.OAuthProviderMissMatchException;
import com.gdsc.timerservice.oauth.model.TokenResponse;
import com.gdsc.timerservice.oauth.service.IssueRefreshService;
import com.gdsc.timerservice.oauth.token.AppleOAuthRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuth2Service oAuth2Service;
    private final IssueRefreshService issueRefreshService;

    private final AppleOAuthRetriever appleOAuthRetriever;

    /**
     * 사용자의 카카오 로그인 요청 후, 로그인한 다음 리다이렉트된 경로.<br/><br/>
     * 카카오로 총 두번의 HTTP 요청으로 사용자 정보(이메일, 이름) 를 알아온다. <br/>
     * 최종적으로 반환받은 사용자 정보로 강제 회원가입 절차를 진행한다. <br/><br/>
     * 1. 로그인 성공 후 받은 쿼리파리미터의 code 를 이용해 카카오 인증 서버에 access token 요청<br/>
     * 2. 받은 access token 을 가지고 카카오 리소스 서버에 사용자 정보 요청
     */
    @GetMapping(value = "/callback/{vendor}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> codeFromKakao(@RequestParam String code,
                                           @PathVariable String vendor) {
        // 강제 회원가입 시작
        try {
            var tokenResponse = oAuth2Service.socialJoin(code, vendor);
            return ResponseEntity.ok()
                    .body(tokenResponse);
        } catch (OAuthProviderMissMatchException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestHeader(name = "refresh_token") String refreshToken) {
        TokenResponse tokenResponse = issueRefreshService.refreshToken(refreshToken);
        return ResponseEntity.ok()
                .body(tokenResponse);
    }


}
