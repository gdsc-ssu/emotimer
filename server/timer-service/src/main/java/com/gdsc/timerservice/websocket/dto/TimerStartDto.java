package com.gdsc.timerservice.websocket.dto;

import lombok.Getter;

@Getter
public class TimerStartDto {

    int userId;
    int startTime; // 서버 시간을 기준으로 시작시간을 client에서 전송
}