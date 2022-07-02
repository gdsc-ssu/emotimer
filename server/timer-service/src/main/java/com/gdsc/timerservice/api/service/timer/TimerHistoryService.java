package com.gdsc.timerservice.api.service.timer;

import com.gdsc.timerservice.api.dtos.timer.response.GetTimerStatisticsResponse;
import com.gdsc.timerservice.api.dtos.timerhistory.request.CreateTimerHistoryRequest;
import com.gdsc.timerservice.api.entity.timer.TimerHistory;
import com.gdsc.timerservice.api.repository.timer.TimerHistoryRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimerHistoryService {

	private final TimerHistoryRepository timerHistoryRepository;

	public GetTimerStatisticsResponse getTimerStatistics(int year, Integer month, Integer day) {
		return timerHistoryRepository.getTimerStatistics(year, month, day);
	}

	public void createTimerHistory(CreateTimerHistoryRequest createTimerHistoryRequest) {
		LocalDateTime now = LocalDateTime.now();

		TimerHistory.builder()
			.userId(createTimerHistoryRequest.getUserId())
			.dateTime(now)
			.year(now.getYear())
			.month(now.getMonth().getValue())
			.day(now.getDayOfMonth())
			.totalSeconds(createTimerHistoryRequest.getTotalSeconds())
			.succeed(createTimerHistoryRequest.isSucceed())
			.category(createTimerHistoryRequest.getCategory());
	}
}