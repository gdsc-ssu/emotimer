package com.gdsc.timerservice.api.repository.timer;

import com.gdsc.timerservice.api.dtos.timerhistory.TimerStatistics;
import java.util.List;

public interface TimerHistoryRepositoryCustom {

	List<TimerStatistics> getTimerStatistics(String userId, int year);

	List<TimerStatistics> getTimerStatistics(String userId, int year, int month);

	List<TimerStatistics> getTimerStatistics(String userId, int year, int month, int week);
}