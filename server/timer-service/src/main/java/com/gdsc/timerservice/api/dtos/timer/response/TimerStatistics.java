package com.gdsc.timerservice.api.dtos.timer.response;

import com.gdsc.timerservice.common.enums.Emoji;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class TimerStatistics {

	private Emoji category; // TODO User Setting 작업 후 Emoji -> Category로 변경

	private long totalSeconds;

	@QueryProjection
	public TimerStatistics(Emoji category, long totalSeconds) {
		this.category = category;
		this.totalSeconds = totalSeconds;
	}
}
