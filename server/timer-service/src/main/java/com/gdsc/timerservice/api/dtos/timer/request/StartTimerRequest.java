package com.gdsc.timerservice.api.dtos.timer.request;

import com.gdsc.timerservice.common.enums.Emoji;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartTimerRequest {

	private long userId;

	private long totalTime;

	private Emoji categorty;

	private LocalDateTime startTime;
}
