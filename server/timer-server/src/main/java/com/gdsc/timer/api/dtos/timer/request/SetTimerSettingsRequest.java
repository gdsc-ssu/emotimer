package com.gdsc.timerservice.api.dtos.timer.request;

import com.gdsc.timerservice.common.enums.Emoji;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetTimerSettingsRequest {

	private long totalTime;

	private Emoji emoji;
}
