package com.gdsc.timerservice.api.dtos.timerhistory.queryprojection;

import com.gdsc.timerservice.common.enums.Emoji;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class TimerStatisticsOfMonthQueryResult extends TimerStatisticsQueryResult {

	private int week;

	@QueryProjection
	public TimerStatisticsOfMonthQueryResult(int week, Emoji emoji, long totalSeconds) {
		super(emoji, totalSeconds);
		this.week = week;
	}
}
