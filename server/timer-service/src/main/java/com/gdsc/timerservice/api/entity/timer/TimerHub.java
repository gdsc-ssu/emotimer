package com.gdsc.timerservice.api.entity.timer;

import com.gdsc.timerservice.common.enums.Emoji;
import com.gdsc.timerservice.timer.enums.TimerStatus;

import javax.persistence.*;

@Entity
public class TimerHub {

    @Id
    @GeneratedValue
    @Column
    private long timerHubId;

    @Column
    private long userId;

    @Column
    private Emoji Emoji;

    @Column
    @Enumerated(EnumType.STRING)
    private TimerStatus timerStatus;
}