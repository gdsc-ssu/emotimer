package com.gdsc.timerservice.api.dtos.timerhistory.queryprojection;

import com.gdsc.timerservice.common.enums.Emoji;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class QueryForTimerStatisticsOfYear {

	private int month;

	private Emoji emoji;

	private long totalSeconds;

	@QueryProjection
	public QueryForTimerStatisticsOfYear(int month, Emoji emoji, long totalSeconds) {
		this.month = month;
		this.emoji = emoji;
		this.totalSeconds = totalSeconds;
	}
}
