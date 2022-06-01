package com.gdsc.timerservice.websocket.dto;

import com.gdsc.timerservice.timer.enums.TimerStatus;
import lombok.Getter;

@Getter
public class Timer {

    private long userId;

    private TimerStatus timerStatus;
}
