package com.gdsc.timerservice.api.controller.timer;

import com.gdsc.timerservice.api.dtos.TimerRestDto;
import com.gdsc.timerservice.api.service.timer.TimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/timer")
@RequiredArgsConstructor
public class TimerController {
    private final TimerService timerService;

    public ResponseEntity<Object> makeTimerHub(TimerRestDto timer) {
        timerService.makeTimerHub(timer);

        return ResponseEntity.ok("ok");
    }
}
