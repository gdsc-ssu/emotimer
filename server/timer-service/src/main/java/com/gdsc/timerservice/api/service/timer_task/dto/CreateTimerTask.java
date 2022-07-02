package com.gdsc.timerservice.api.service.timer_task.dto;

import com.gdsc.timerservice.common.enums.Emoji;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CreateTimerTask {

	private long userId;

	private long remainedSeconds;

	private Emoji category;

	private LocalDateTime serverTime;
}
