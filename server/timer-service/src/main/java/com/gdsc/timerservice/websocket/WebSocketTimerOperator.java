package com.gdsc.timerservice.websocket;

import com.gdsc.timerservice.websocket.dto.request.WebSocketChangeTimerSettingsRequest;
import com.gdsc.timerservice.websocket.dto.request.WebSocketTimerOperationRequest;
import com.gdsc.timerservice.websocket.dto.response.WebSocketChangeTimerSettingsResponse;
import com.gdsc.timerservice.websocket.dto.response.WebSocketTimerOperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketTimerOperator {

	private final SimpMessageSendingOperations messagingTemplate;

	public void operateTimer(WebSocketTimerOperationRequest timerOperation) {
		WebSocketTimerOperationResponse response = WebSocketTimerOperationResponse.builder()
			.serverTime(timerOperation.getServerTime())
			.timerOperation(timerOperation.getTimerOperation()).build();

		messagingTemplate.convertAndSend("/sub/timer/" + timerOperation.getUserId(), response);
	}

	public void changeTimerSetting(WebSocketChangeTimerSettingsRequest timerSettings) {
		WebSocketChangeTimerSettingsResponse response = WebSocketChangeTimerSettingsResponse.builder()
			.totalTimeSeconds(timerSettings.getTotalTimeSeconds())
			.emoji(timerSettings.getEmoji()).build();

		messagingTemplate.convertAndSend("/sub/timer/" + timerSettings.getUserId(), response);
	}
}