package com.gdsc.timerservice.api.controller.user;

import com.gdsc.timerservice.api.dtos.user.UserSettingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/") // 메인 페이지. 소셜 로그인할 수 있는 링크들 있음.
    public String hello() {
        return "index";
    }

    @PatchMapping("/setting")
    public ResponseEntity setUserSetting(UserSettingRequest request, @AuthenticationPrincipal User user) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(user);
        return ResponseEntity.ok().body("OK");
    }

}
