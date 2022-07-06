package com.gdsc.timerservice.api.dtos.timerhistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WeekOfMonth {

	private int month;

	private int week;
}
