package com.gdsc.timerservice.api.dtos.timerhistory.queryprojection;

import com.gdsc.timerservice.common.enums.Emoji;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class TimerStatisticsOfWeekQueryResult extends TimerStatisticsQueryResult {

	private String dayOfWeek;

	@QueryProjection
	public TimerStatisticsOfWeekQueryResult(String dayOfWeek, Emoji emoji, long totalSeconds) {
		super(emoji, totalSeconds);
		this.dayOfWeek = dayOfWeek;
	}
}
