package com.gdsc.timerservice.api.controller.timer;

import com.gdsc.timerservice.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/timer")
@RequiredArgsConstructor
public class TimerController {
    private final UserService userService;
}
