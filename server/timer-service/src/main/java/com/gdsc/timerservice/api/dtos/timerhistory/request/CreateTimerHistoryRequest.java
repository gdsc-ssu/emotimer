package com.gdsc.timerservice.api.dtos.timerhistory.request;

import com.gdsc.timerservice.common.enums.Emoji;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTimerHistoryRequest {

	private long userId;

	private long totalSeconds;

	private long remainedSeconds;

	private boolean succeed;

	private Emoji category;
}
