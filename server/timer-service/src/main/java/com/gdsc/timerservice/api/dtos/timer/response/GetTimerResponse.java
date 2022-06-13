package com.gdsc.timerservice.api.dtos.timer.response;


import com.gdsc.timerservice.api.entity.timer.TimerHub;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetTimerResponse {
    private TimerHub timerHub;
}
