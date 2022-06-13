package com.gdsc.timerservice.api.controller.timer;

import com.gdsc.timerservice.api.dtos.timer.request.MakeTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.response.CreateTimerResponse;
import com.gdsc.timerservice.api.dtos.timer.response.GetServerTimeResponse;
import com.gdsc.timerservice.api.dtos.timer.response.GetTimerResponse;
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

	@PostMapping("")
	public ResponseEntity<CreateTimerResponse> createTimer(MakeTimerRequest req) {
		return ResponseEntity.ok(timerService.createTimer(req));
	}

	// TODO security 파트와 이야기해서 userId 추출하여 userId로 timer를 가져오는 것으로 변경하기
	@GetMapping("/{timerId}")
	public ResponseEntity<GetTimerResponse> getTimerHub(@PathVariable long timerId) {
		return ResponseEntity.ok(timerService.getTimerHub(timerId));
	}

//    @PostMapping("/operate")
//    public void operateTimer(OperateTimerRequest operateTimerRequest) {
//        timerService.operateTimer(operateTimerRequest);
//    }
}
