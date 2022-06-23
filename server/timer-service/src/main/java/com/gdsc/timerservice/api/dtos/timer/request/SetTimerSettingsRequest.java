package com.gdsc.timerservice.api.dtos.timer.request;

import com.gdsc.timerservice.common.enums.Emoji;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SetTimerSettingsRequest {

	private long userId;

	private long totalTime;

	private Emoji category; // TODO : 나중에 UserSetting 파트에서 Category Enum 클래스를 만들면 그걸로 변경
}
