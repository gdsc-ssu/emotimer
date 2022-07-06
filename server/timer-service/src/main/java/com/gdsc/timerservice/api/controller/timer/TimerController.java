package com.gdsc.timerservice.api.controller.timer;

import com.gdsc.timerservice.api.dtos.timer.request.PauseTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.request.ResetTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.request.ResumeTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.request.SetTimerSettingsRequest;
import com.gdsc.timerservice.api.dtos.timer.request.StartTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.response.GetServerTimeResponse;
import com.gdsc.timerservice.api.dtos.timer.response.GetTimerResponse;
import com.gdsc.timerservice.api.dtos.timer.response.SetTimerSettingsResponse;
import com.gdsc.timerservice.api.service.timer.TimerService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/timer")
@RequiredArgsConstructor
public class TimerController {

	private final TimerService timerService;

	@GetMapping("/server-time")
	public ResponseEntity<GetServerTimeResponse> getServerTime() {
		return ResponseEntity.ok(GetServerTimeResponse.builder().now(LocalDateTime.now()).build());
	}

	@PostMapping("/set")
	public ResponseEntity<SetTimerSettingsResponse> setTimerSettings(@RequestBody SetTimerSettingsRequest setTimerSettingsRequest) {
		return ResponseEntity.ok(timerService.setTimerSettings(setTimerSettingsRequest));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<GetTimerResponse> getTimer(@PathVariable String userId) {
		return ResponseEntity.ok(timerService.getTimer(userId));
	}

	@PostMapping("/start")
	public ResponseEntity startTimer(@RequestBody StartTimerRequest startTimerRequest) {
		timerService.startTimer(startTimerRequest);
		return ResponseEntity.ok("");
	}

	@PostMapping("/pause")
	public ResponseEntity pauseTimer(@RequestBody PauseTimerRequest pauseTimerRequest) {
		timerService.pauseTimer(pauseTimerRequest);
		return ResponseEntity.ok("");
	}

	@PostMapping("/resume")
	public ResponseEntity resumeTimer(@RequestBody ResumeTimerRequest resumeTimerRequest) {
		timerService.resumeTimer(resumeTimerRequest);
		return ResponseEntity.ok("");
	}

	@PostMapping("/reset")
	public ResponseEntity resetTimer(@RequestBody ResetTimerRequest resetTimerRequest) {
		timerService.resetTimer(resetTimerRequest);
		return ResponseEntity.ok("");
	}
}