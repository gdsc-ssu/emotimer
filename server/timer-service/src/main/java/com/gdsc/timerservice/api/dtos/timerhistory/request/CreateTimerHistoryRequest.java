package com.gdsc.timerservice.api.dtos.timerhistory.request;

import com.gdsc.timerservice.common.enums.Emoji;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTimerHistoryRequest {

	private long userId;

	@Deprecated
	private long totalSeconds;

	@Deprecated
	private long remainedSeconds;

	private long spentTime;

	private boolean succeed;

	private Emoji category;
}
