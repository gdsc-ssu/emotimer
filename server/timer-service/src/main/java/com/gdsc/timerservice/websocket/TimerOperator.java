package com.gdsc.timerservice.websocket;

import com.gdsc.timerservice.websocket.dto.TimerWebSocketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TimerOperator {
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/timer")
    public void timerStart(TimerWebSocketDto timerWebSocketDto) {
        messagingTemplate.convertAndSend("sub/timer/"+ timerWebSocketDto.getUserId(), timerWebSocketDto);
    }
}
