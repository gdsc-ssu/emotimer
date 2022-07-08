package com.gdsc.timerservice.api.service.timer;

import static com.gdsc.timerservice.api.service.timer.CalendarUtil.getCurrentWeekOfMonth;

import com.gdsc.timerservice.api.dtos.timerhistory.TimerStatistics;
import com.gdsc.timerservice.api.dtos.timerhistory.WeekOfMonth;
import com.gdsc.timerservice.api.dtos.timerhistory.request.CreateTimerHistoryRequest;
import com.gdsc.timerservice.api.entity.timer.TimerHistory;
import com.gdsc.timerservice.api.repository.timer.TimerHistoryRepository;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimerHistoryService {

	private final TimerHistoryRepository timerHistoryRepository;

	public List<TimerStatistics> getTimerStatisticsOfYear(String userId, int year, Integer month, Integer week) {
		if (month == null && week == null) {
			return timerHistoryRepository.getTimerStatistics(userId, year);
		}

		if (month != null && week == null) {
			return timerHistoryRepository.getTimerStatistics(userId, year, month);
		}

		if (month != null && week != null) {
			return timerHistoryRepository.getTimerStatistics(userId, year, month, week);
		}

		return null;
	}

	public void createTimerHistory(CreateTimerHistoryRequest createTimerHistoryRequest) {
		LocalDateTime now = LocalDateTime.now();

		int year = now.getYear();
		int month = now.getMonth().getValue();
		int day = now.getDayOfMonth();
		String dayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);

		WeekOfMonth weekOfMonth = getCurrentWeekOfMonth(year, month, day);

		TimerHistory timerHistory = TimerHistory.builder()
			.userId(createTimerHistoryRequest.getUserId())
			.dateTime(now)
			.year(year)
			.month(month)
			.monthForStatistics(weekOfMonth.getMonth())
			.week(weekOfMonth.getWeek())
			.day(day)
			.dayOfWeek(dayOfWeek)
			.spentSeconds(createTimerHistoryRequest.getSpentTime())
			.succeed(createTimerHistoryRequest.isSucceed())
			.emoji(createTimerHistoryRequest.getEmoji()).build();

		timerHistoryRepository.save(timerHistory);
	}

}