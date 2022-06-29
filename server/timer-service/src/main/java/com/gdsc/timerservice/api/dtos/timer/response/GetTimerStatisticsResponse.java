package com.gdsc.timerservice.api.dtos.timer.response;

import java.util.List;
import lombok.Setter;

@Setter
public class GetTimerStatisticsResponse {

	List<TimerStatistics> timerStatisticsList;
}
