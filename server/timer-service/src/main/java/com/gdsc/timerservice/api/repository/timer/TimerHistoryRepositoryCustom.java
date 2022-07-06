package com.gdsc.timerservice.api.repository.timer;

import com.gdsc.timerservice.api.dtos.timerhistory.response.GetTimerStatisticsOfYearResponse;

public interface TimerHistoryRepositoryCustom {

	GetTimerStatisticsOfYearResponse getTimerStatisticsOfYear(String userId, int year);
}