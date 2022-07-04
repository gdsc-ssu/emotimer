package com.gdsc.timerservice.api.controller.timer;

import com.gdsc.timerservice.api.dtos.timer.response.GetTimerStatisticsResponse;
import com.gdsc.timerservice.api.service.timer.TimerHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/history")
public class TimerHistoryController {

	private final TimerHistoryService timerHistoryService;

	@GetMapping("")
	public GetTimerStatisticsResponse getTimerStatistics(
		@RequestParam(value = "userId") String userId, // userId는 이후 requestparam말고 security를 통해서 주입받는 것으로 변경
		@RequestParam(value = "year") int year,
		@RequestParam(value = "month", required = false) Integer month,
		@RequestParam(value = "day", required = false) Integer day
	) {
		return timerHistoryService.getTimerStatistics(userId, year, month, day);
	}
}