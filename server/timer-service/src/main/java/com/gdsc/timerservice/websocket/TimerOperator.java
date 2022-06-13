package com.gdsc.timerservice.websocket;

import com.gdsc.timerservice.websocket.dto.WebSocketTimerOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimerOperator {
    private final SimpMessageSendingOperations messagingTemplate;

    // TODO 함수를 static으로 바꾸고 싶은데 그럴려면 channel을 직접 넣어줘야 할지 고민
    @MessageMapping("/timer")
    public void operateTimer(WebSocketTimerOperation timerWebSocket) {
        messagingTemplate.convertAndSend("sub/timer/"+ timerWebSocket.getUserId(), timerWebSocket);
    }
}