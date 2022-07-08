package com.gdsc.timerservice.api.controller.auth;

import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.service.auth.OAuth2Service;
import com.gdsc.timerservice.oauth.entity.RoleType;
import com.gdsc.timerservice.oauth.exception.OAuthProviderMissMatchException;
import com.gdsc.timerservice.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.gdsc.timerservice.oauth.model.TokenResponse;
import com.gdsc.timerservice.oauth.service.IssueRefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuth2Service oAuth2Service;
    private final IssueRefreshService issueRefreshService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    /**
     * 사용자의 카카오 로그인 요청 후, 로그인한 다음 리다이렉트된 경로.<br/><br/> 카카오로 총 두번의 HTTP 요청으로 사용자 정보(이메일, 이름) 를 알아온다. <br/> 최종적으로 반환받은 사용자 정보로 강제 회원가입 절차를 진행한다. <br/><br/> 1. 로그인 성공 후 받은 쿼리파리미터의 code 를 이용해 카카오 인증 서버에 access token 요청<br/> 2. 받은 access token 을 가지고 카카오 리소스 서버에 사용자 정보 요청
     */
    @GetMapping(value = "/callback/{vendor}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity codeFromKakao(@RequestParam String code, @PathVariable String vendor) throws IOException {
        // 강제 회원가입 시작
        try {
            User user = oAuth2Service.socialJoin(code, vendor);
            TokenResponse tokenResponse = oAuth2Service.generateToken(user, user.getEmail(), RoleType.USER);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(UriComponentsBuilder.fromUriString("emotimer://success")
                    .queryParam("accessToken", tokenResponse.getAccessToken())
                    .queryParam("refreshToken", tokenResponse.getRefreshToken())
                    .build().toUri());
            return new ResponseEntity(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (OAuthProviderMissMatchException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity refresh(HttpServletRequest request) {
        return ResponseEntity.ok(issueRefreshService.refreshToken(request));
    }
}
