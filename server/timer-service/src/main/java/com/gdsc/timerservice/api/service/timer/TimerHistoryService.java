package com.gdsc.timerservice.api.service.timer;

import com.gdsc.timerservice.api.dtos.timer.response.GetTimerStatisticsResponse;
import com.gdsc.timerservice.api.repository.timer.TimerHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimerHistoryService {

	private final TimerHistoryRepository timerHistoryRepository;

	public GetTimerStatisticsResponse getTimerStatistics(int year, Integer month, Integer day) {
		return timerHistoryRepository.getTimerStatistics(year, month, day);
	}
}
