package com.gdsc.timerservice.api.dtos;

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
public class TimerRestDto {

    private long userId;

    private LocalDateTime startedAt;

    //Milli
    private Long totalSeconds;

    //Milli
    private Long remainedSeconds;

    private Emoji emoji;

    private TimerStatus timerStatus;
}
