package com.gdsc.timerservice.api.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserController {
    @GetMapping("/")
    public String hello(){
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

}
