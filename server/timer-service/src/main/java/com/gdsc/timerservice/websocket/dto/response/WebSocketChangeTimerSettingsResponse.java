package com.gdsc.timerservice.websocket.dto.response;

import com.gdsc.timerservice.common.enums.Emoji;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WebSocketChangeTimerSettingsResponse {

	private long totalTimeSeconds;

	private Emoji emoji;
}
