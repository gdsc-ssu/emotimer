package com.gdsc.timerservice.api.controller.user;

import com.gdsc.timerservice.oauth.entity.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    @GetMapping("/") // 메인 페이지. 소셜 로그인할 수 있는 링크들 있음.
    public String hello() {
        return "index";
    }

}
