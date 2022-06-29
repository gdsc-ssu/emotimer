package com.gdsc.timerservice.api.dtos.timer.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTimerStatisticsResponse {

	List<TimerStatistics> timerStatisticsList = new ArrayList<>();
}
