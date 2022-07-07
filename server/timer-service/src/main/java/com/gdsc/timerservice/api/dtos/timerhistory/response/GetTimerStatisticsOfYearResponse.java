package com.gdsc.timerservice.api.dtos.timerhistory.response;

import com.gdsc.timerservice.api.dtos.timerhistory.TimerStatistics;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GetTimerStatisticsOfYearResponse {

	List<TimerStatistics> timerStatistics;
}
