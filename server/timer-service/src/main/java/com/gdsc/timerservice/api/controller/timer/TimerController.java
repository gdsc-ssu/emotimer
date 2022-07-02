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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/timer")
@RequiredArgsConstructor
public class TimerController {

	private final TimerService timerService;

	@GetMapping
	public ResponseEntity<GetServerTimeResponse> getServerTime() {
		return ResponseEntity.ok(GetServerTimeResponse.builder().now(LocalDateTime.now()).build());
	}

	@PostMapping("/set")
	public ResponseEntity<SetTimerSettingsResponse> setTimerSettings(SetTimerSettingsRequest setTimerSettingsRequest) {
		return ResponseEntity.ok(timerService.setTimerSettings(setTimerSettingsRequest));
	}

	// TODO security 파트와 이야기해서 userId 추출하여 userId로 timer를 가져오는 것으로 변경하기
	@GetMapping("/{userId}")
	public ResponseEntity<GetTimerResponse> getTimer(@PathVariable long userId) {
		return ResponseEntity.ok(timerService.getTimer(userId));
	}

	@PostMapping("/start")
	public ResponseEntity startTimer(StartTimerRequest startTimerRequest) {
		timerService.startTimer(startTimerRequest);
		return ResponseEntity.ok("");
	}

	@PostMapping("/pause")
	public ResponseEntity pauseTimer(PauseTimerRequest pauseTimerRequest) {
		timerService.pauseTimer(pauseTimerRequest);
		return ResponseEntity.ok("");
	}

	@PostMapping("/resume")
	public ResponseEntity resumeTimer(ResumeTimerRequest resumeTimerRequest) {
		timerService.resumeTimer(resumeTimerRequest);
		return ResponseEntity.ok("");
	}

	@PostMapping("/reset")
	public ResponseEntity resetTimer(ResetTimerRequest resetTimerRequest) {
		timerService.resetTimer(resetTimerRequest);
		return ResponseEntity.ok("");
	}
}