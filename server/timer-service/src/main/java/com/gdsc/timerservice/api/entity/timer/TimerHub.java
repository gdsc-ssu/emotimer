package com.gdsc.timerservice.api.entity.timer;

import com.gdsc.timerservice.common.enums.Emoji;
import com.gdsc.timerservice.common.enums.TimerStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    //TODO CategoryId로 변경될 수도 있음
    @Column
    private Emoji emoji;

    @Column
    @Enumerated(EnumType.STRING)
    private TimerStatus timerStatus;
}