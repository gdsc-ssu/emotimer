package com.gdsc.timerservice.timer.websocket.dto;

import lombok.Getter;

@Getter
public class TimerPauseDto {

    int userId;
    long remainTimeInMillis; // 서버 시간을 기준으로 중단 시간을 client에 전송
}
