package com.gdsc.timerservice.api.controller.timer;

import com.gdsc.timerservice.api.dtos.TimerRestDto;
import com.gdsc.timerservice.api.service.timer.TimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/timer")
@RequiredArgsConstructor
public class TimerController {
    private final TimerService timerService;

    @PostMapping("/make")
    public ResponseEntity<TimerRestDto> makeTimer(TimerRestDto timer) {
        return ResponseEntity.ok(timerService.makeTimer(timer));
    }

    // TODO security 파트와 이야기해서 userId 추출하여 userId로 timer를 가져오는 것으로 변경하기
    @GetMapping("/get/{timerId}")
    public ResponseEntity<TimerRestDto> getTimerHub(@PathVariable long timerId) {
        return ResponseEntity.ok(timerService.getTimerHub(timerId));
    }
}
