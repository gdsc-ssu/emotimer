package com.gdsc.timerservice.api.controller.timer;

import com.gdsc.timerservice.api.dtos.timerhistory.response.GetTimerStatisticsOfYearResponse;
import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.service.timer.TimerHistoryService;
import com.gdsc.timerservice.common.annotation.CurrentUser;
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
	public GetTimerStatisticsOfYearResponse getTimerStatisticsOfYear(
		@RequestParam(value = "year") int year,
		@RequestParam(value = "month", required = false) Integer month,
		@RequestParam(value = "week", required = false) Integer week,
		@CurrentUser User user
	) {
		return new GetTimerStatisticsOfYearResponse(timerHistoryService.getTimerStatisticsOfYear(user.getUserId(), year, month, week));
	}
}