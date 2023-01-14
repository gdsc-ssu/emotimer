package com.gdsc.timerservice.api.dtos.timerhistory.queryprojection;

import com.gdsc.timerservice.common.enums.Emoji;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public abstract class TimerStatisticsQueryResult {

	private Emoji emoji;

	private long totalSeconds;

	public TimerStatisticsQueryResult(Emoji emoji, long totalSeconds) {
		this.emoji = emoji;
		this.totalSeconds = totalSeconds;
	}
}
