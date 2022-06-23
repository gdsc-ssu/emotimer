package com.gdsc.timerservice.api.dtos.timer.response;

import com.gdsc.timerservice.common.enums.Emoji;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SetTimerSettingsResponse {

	private LocalDateTime startedAt;

	private Long totalTimeSeconds;

	private Long remainedSeconds;

	private Emoji category; // 추후 User Settings 이후에 Emoji 클래스를 Category로 변경할 예정
}
