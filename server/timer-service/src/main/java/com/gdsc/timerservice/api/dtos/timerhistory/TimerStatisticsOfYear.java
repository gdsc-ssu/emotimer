package com.gdsc.timerservice.api.dtos.timerhistory;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimerStatisticsOfYear {

	private int month;

	private List<TimerStatistics> timerStatistics = new ArrayList<>();
}