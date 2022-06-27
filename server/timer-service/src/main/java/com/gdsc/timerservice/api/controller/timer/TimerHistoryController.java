package com.gdsc.timerservice.api.controller.timer;

import com.gdsc.timerservice.api.service.timer.TimerHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TimerHistoryController {

	private final TimerHistoryService timerHistoryService;

	@GetMapping
	public void getTimerStatics(
		@RequestParam(value = "year", required = true) int year,
		@RequestParam(value = "month", required = false) Integer month,
		@RequestParam(value = "day", required = false) Integer day
	) {
		timerHistoryService.getTimerStatics(year, month, day);
	}
}