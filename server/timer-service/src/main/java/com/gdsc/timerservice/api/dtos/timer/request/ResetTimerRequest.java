package com.gdsc.timerservice.api.dtos.timer.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetTimerRequest {

	private String userId;

	private long remainedSeconds;
}
