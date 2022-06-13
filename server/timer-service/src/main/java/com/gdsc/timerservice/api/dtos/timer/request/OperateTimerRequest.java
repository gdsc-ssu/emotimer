package com.gdsc.timerservice.api.dtos.timer.request;

import com.gdsc.timerservice.websocket.enums.TimerOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OperateTimerRequest {
    private long userId;

    private long serverTime;

    private TimerOperation timerOperation;
}
