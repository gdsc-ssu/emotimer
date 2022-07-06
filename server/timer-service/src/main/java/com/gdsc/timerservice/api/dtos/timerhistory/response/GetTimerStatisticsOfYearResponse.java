package com.gdsc.timerservice.api.dtos.timerhistory.response;

import com.gdsc.timerservice.api.dtos.timerhistory.TimerStatisticsOfYear;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GetTimerStatisticsOfYearResponse {

	List<TimerStatisticsOfYear> timerStatisticsOfYear = new ArrayList<>();
}
