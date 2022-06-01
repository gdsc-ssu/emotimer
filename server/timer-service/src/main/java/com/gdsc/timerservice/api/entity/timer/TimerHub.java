package com.gdsc.timerservice.api.entity.timer;

import com.gdsc.timerservice.common.enums.Emoji;
import com.gdsc.timerservice.common.enums.TimerStatus;
import com.gdsc.timerservice.websocket.enums.TimerOperation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TimerHub {

    @Id
    @GeneratedValue
    @Column
    private long timerHubId;

    @Column
    private long userId;

    @Column
    private LocalDateTime startedAt;

    //Milli
    @Column
    private Long totalSeconds;

    //Milli
    @Column
    private Long remainedSeconds;

    @Column
    private Emoji Emoji;

    @Column
    @Enumerated(EnumType.STRING)
    private TimerStatus timerStatus;
}