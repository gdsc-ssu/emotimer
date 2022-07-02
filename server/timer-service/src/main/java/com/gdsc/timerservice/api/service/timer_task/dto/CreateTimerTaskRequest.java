package com.gdsc.timerservice.api.service.timer_task.dto;

import com.gdsc.timerservice.common.enums.Emoji;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTimerTaskRequest {

	private long userId;

	private long remainedSeconds;

	private Emoji category;
}
