package com.gdsc.timerservice.api.dtos.timerhistory.queryprojection;

import com.gdsc.timerservice.common.enums.Emoji;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class TimerStatisticsOfYearQueryResult extends TimerStatisticsQueryResult {

	private int month;

	@QueryProjection
	public TimerStatisticsOfYearQueryResult(int month, Emoji emoji, long totalSeconds) {
		super(emoji, totalSeconds);
		this.month = month;
	}
}
