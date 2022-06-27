package com.gdsc.timerservice.api.dtos.timer.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTimerStaticsResponse {

	enum Example {
		DAY, MONTH, YEAR
	}
}
