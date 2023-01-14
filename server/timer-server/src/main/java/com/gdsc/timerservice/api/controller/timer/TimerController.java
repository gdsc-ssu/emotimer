package com.gdsc.timerservice.api.controller.timer;

import com.gdsc.timerservice.api.dtos.timer.request.PauseTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.request.ResetTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.request.ResumeTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.request.SetTimerSettingsRequest;
import com.gdsc.timerservice.api.dtos.timer.request.StartTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.response.GetServerTimeResponse;
import com.gdsc.timerservice.api.dtos.timer.response.GetTimerResponse;
import com.gdsc.timerservice.api.dtos.timer.response.SetTimerSettingsResponse;
import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.service.timer.TimerService;
import com.gdsc.timerservice.common.annotation.CurrentUser;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timer")
@RequiredArgsConstructor
public class TimerController {

    private final TimerService timerService;

    @GetMapping("/server-time")
    public ResponseEntity<GetServerTimeResponse> getServerTime() {
        return ResponseEntity.ok(GetServerTimeResponse.builder().now(LocalDateTime.now()).build());
    }

    @PostMapping()
    public ResponseEntity<SetTimerSettingsResponse> setTimerSettings(@CurrentUser User user, @RequestBody SetTimerSettingsRequest setTimerSettingsRequest) {
        return ResponseEntity.ok(timerService.setTimerSettings(setTimerSettingsRequest, user.getUserId()));
    }

    @GetMapping()
    public ResponseEntity<GetTimerResponse> getTimer(@CurrentUser User user) {
        return timerService.getTimer(user.getUserId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/start")
    public ResponseEntity startTimer(@RequestBody StartTimerRequest startTimerRequest, @CurrentUser User user) {
        timerService.startTimer(startTimerRequest, user.getUserId());
        return ResponseEntity.ok("");
    }

    @PostMapping("/pause")
    public ResponseEntity pauseTimer(@RequestBody PauseTimerRequest pauseTimerRequest, @CurrentUser User user) {
        timerService.pauseTimer(pauseTimerRequest, user.getUserId());
        return ResponseEntity.ok("");
    }

    @PostMapping("/resume")
    public ResponseEntity resumeTimer(@RequestBody ResumeTimerRequest resumeTimerRequest, @CurrentUser User user) {
        timerService.resumeTimer(resumeTimerRequest, user.getUserId());
        return ResponseEntity.ok("");
    }

    @PostMapping("/reset")
    public ResponseEntity resetTimer(@RequestBody ResetTimerRequest resetTimerRequest, @CurrentUser User user) {
        timerService.resetTimer(resetTimerRequest, user.getUserId());
        return ResponseEntity.ok("");
    }
}