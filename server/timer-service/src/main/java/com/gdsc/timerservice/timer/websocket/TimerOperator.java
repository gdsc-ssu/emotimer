package com.gdsc.timerservice.timer.websocket;

import com.gdsc.timerservice.timer.websocket.dto.TimerStartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TimerOperator {
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/timer/start")
    public void timerStart(TimerStartDto timerStartDto) {
        messagingTemplate.convertAndSend("sub/timer/"+ timerStartDto.getUserId(), timerStartDto);
    }

    @MessageMapping("/timer/pause")
    public void timerPause() {
        messagingTemplate.convertAndSend("sub/pause/timer");
    }
}
