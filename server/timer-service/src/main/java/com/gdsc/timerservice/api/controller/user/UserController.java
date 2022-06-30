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

//    @PostMapping("/test") // POST 요청은 csrf 설정을 따로 해주지 않는다면 다른 서버에서 POST 등 리소스의 상태를 변경하는 동작 아예 수행 불가.
//    public String test(){
//        return "i am data from POST request";
//    }
//    @GetMapping("/test") // GET 요청은 csrf 에서 자유로움.
//    public String tt(){
//        return "i am data from GET request";
//    }

//    @GetMapping("/email")
//    public @ResponseBody String getUserEmail(@AuthenticationPrincipal UserPrincipal user){ // 세션 정책을 STATELESS 하게 설정했기 때문에 세션에 유저정보 저장 안함. 그래서 당연히 못가져오는듯. null 값!
////        log.info("현재 로그인된 유저의 이메일 정보 가져오기: {}", principal.getUser().getEmail());
//        log.info(String.valueOf(user));
//        return "유저 정보";
//    }

}
