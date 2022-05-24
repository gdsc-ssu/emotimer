package com.gdsc.timerservice.api.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserController {
    @GetMapping("/")
    public String hello(){
        return "index";
    }

}
