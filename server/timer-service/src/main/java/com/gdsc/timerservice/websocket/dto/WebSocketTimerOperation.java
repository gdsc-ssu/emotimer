package com.gdsc.timerservice.websocket.dto;

import com.gdsc.timerservice.websocket.enums.TimerOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WebSocketTimerOperation {

    private long userId;

    //milli
    private long serverTime;

    private TimerOperation timerOperation;
}
