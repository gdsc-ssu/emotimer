package com.gdsc.timerservice.api.dtos.timer.request;

import com.gdsc.timerservice.common.enums.Emoji;
import com.gdsc.timerservice.common.enums.TimerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MakeTimerRequest {

    private long userId;

    private LocalDateTime startedAt;

    //Milli
    private Long totalSeconds;

    //Milli
    private Long remainedSeconds;

    private Emoji emoji; // TODO : categoryId로 변경하게 될 수도 있음

    private TimerStatus timerStatus;
}
