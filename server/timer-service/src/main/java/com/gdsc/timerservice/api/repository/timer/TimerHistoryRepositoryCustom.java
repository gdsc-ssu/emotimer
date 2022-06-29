package com.gdsc.timerservice.api.repository.timer;

import com.gdsc.timerservice.api.dtos.timer.response.GetTimerStatisticsResponse;

public interface TimerHistoryRepositoryCustom {

	GetTimerStatisticsResponse getTimerStatistics(int year, Integer month, Integer day);
}
