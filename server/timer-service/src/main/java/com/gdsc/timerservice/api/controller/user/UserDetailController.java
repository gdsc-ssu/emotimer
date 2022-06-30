package com.gdsc.timerservice.api.controller.user;

import com.gdsc.timerservice.oauth.entity.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@Slf4j
public class UserDetailController {
    /**
     * 현재 로그인된 유저의 이메일 정보를 알고 싶다면, jwt 토큰 디코딩해서 까서 보면 될듯.
     * 세션 정책을 stateless 하게 SecurityConfig.java 에서 정의놨기 때문에 @AuthenticationPrincipal 을 주입 못받는 것 같다.
     */
    @GetMapping("/email")
    public @ResponseBody String getUserEmail(@AuthenticationPrincipal  UserPrincipal user){ // 아.. sessionPolicy 를 STATELESS 하게 설정했기 때문에 못불러오는듯...!
//        log.info("현재 로그인된 유저의 이메일 정보 가져오기: {}", principal.getUser().getEmail());
        log.info(String.valueOf(user));
        return "유저 정보";
    }
}
