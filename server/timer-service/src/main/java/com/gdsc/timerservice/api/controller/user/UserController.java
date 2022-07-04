package com.gdsc.timerservice.api.controller.user;

import com.gdsc.timerservice.api.dtos.user.UserSettingRequest;
import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.common.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final ModelMapper modelMapper;

    @GetMapping("/") // 메인 페이지. 소셜 로그인할 수 있는 링크들 있음.
    public String hello(@CurrentUser User user) {
        return "index";
    }

    @PatchMapping("/setting")
    public ResponseEntity<String> setUserSetting(UserSettingRequest request,
                                         @CurrentUser User user) {
        System.out.println(user.getUserId());
        return ResponseEntity.ok().body("OK");
    }

}
