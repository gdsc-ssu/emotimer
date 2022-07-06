package com.gdsc.timerservice.websocket.dto.request;

import com.gdsc.timerservice.common.enums.Emoji;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WebSocketChangeTimerSettingsRequest {

	private String userId;

	private long totalTimeSeconds;

	private Emoji emoji;
}
