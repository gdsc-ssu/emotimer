package com.gdsc.timerservice.api.dtos.timer.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetTimerStatisticsResponse {

	List<TimerStatistics> timerStatisticsList;
}
