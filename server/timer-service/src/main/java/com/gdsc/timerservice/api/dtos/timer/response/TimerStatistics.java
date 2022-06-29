package com.gdsc.timerservice.api.dtos.timer.response;

import com.gdsc.timerservice.common.enums.Emoji;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimerStatistics {

	private Emoji category; // TODO User Setting 작업 후 Emoji -> Category로 변경

	private long totalSeconds;
}
