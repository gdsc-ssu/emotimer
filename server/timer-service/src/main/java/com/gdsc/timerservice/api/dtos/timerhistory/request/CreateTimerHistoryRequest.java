package com.gdsc.timerservice.api.dtos.timerhistory.request;

import com.gdsc.timerservice.common.enums.Emoji;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTimerHistoryRequest {

	private String userId;

	private long spentTime;

	private boolean succeed;

	private Emoji category;
}
