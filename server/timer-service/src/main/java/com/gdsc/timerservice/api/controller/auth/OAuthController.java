package com.gdsc.timerservice.api.controller.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.service.OAuth2Service;
import com.gdsc.timerservice.oauth.entity.ProviderType;
import com.gdsc.timerservice.oauth.entity.RoleType;
import com.gdsc.timerservice.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.gdsc.timerservice.oauth.model.AbstractProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class OAuthController {


    private final OAuth2Service oAuth2Service;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    /**
     * 사용자의 카카오 로그인 요청 후, 로그인한 다음 리다이렉트된 경로.<br/><br/>
     * 카카오로 총 두번의 HTTP 요청으로 사용자 정보(이메일, 이름) 를 알아온다. <br/>
     * 최종적으로 반환받은 사용자 정보로 강제 회원가입 절차를 진행한다. <br/><br/>
     * 1. 로그인 성공 후 받은 쿼리파리미터의 code 를 이용해 카카오 인증 서버에 access token 요청<br/>
     * 2. 받은 access token 을 가지고 카카오 리소스 서버에 사용자 정보 요청
     */
    @GetMapping("/callback/{vendor}")
    public String codeFromKakao(@RequestParam String code,
                                @PathVariable String vendor,
                                HttpServletResponse response) throws JsonProcessingException {
        // 강제 회원가입 시작
        User user = oAuth2Service.socialJoin(code, vendor);
        // 액세스 토큰과 리프레시 토큰 발급해서 HttpResponse 에 담아 클라이언트 응답으로 반환
        oAuth2Service.generateToken(response, user.getEmail(), RoleType.USER);
        return "kakao success";
    }

    @GetMapping("/callback/apple")
    public String codeFromApple() {

        return null;
    }

}
