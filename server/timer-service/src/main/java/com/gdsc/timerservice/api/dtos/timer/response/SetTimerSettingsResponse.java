package com.gdsc.timerservice.api.dtos.timer.response;

import com.gdsc.timerservice.api.entity.timer.Timer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SetTimerSettingsResponse {

	private Timer timer;
}
