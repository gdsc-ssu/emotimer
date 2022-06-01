package com.gdsc.timerservice.websocket.dto;

import com.gdsc.timerservice.websocket.enums.TimerOperation;
import lombok.Getter;

@Getter
public class Timer {

    private long userId;

    //milli
    private long serverTime;

    private TimerOperation timerStatus;
}
