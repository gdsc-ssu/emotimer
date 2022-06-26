package com.gdsc.timerservice.websocket;

import com.gdsc.timerservice.websocket.dto.WebSocketTimerOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketTimerOperator {

	private final SimpMessageSendingOperations messagingTemplate;

	public void operateTimer(WebSocketTimerOperation timerOperation) {
		messagingTemplate.convertAndSend("sub/timer/" + timerOperation.getUserId(), timerOperation);
	}
}