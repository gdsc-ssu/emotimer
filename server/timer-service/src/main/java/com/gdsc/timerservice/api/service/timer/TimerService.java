package com.gdsc.timerservice.api.service.timer;

import com.gdsc.timerservice.api.dtos.TimerRestDto;
import com.gdsc.timerservice.api.entity.timer.TimerHub;
import com.gdsc.timerservice.api.repository.timer.TimerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimerService {

    private final TimerRepository timerRepository;

    public void makeTimer(TimerRestDto timer) {

        TimerHub timerhub = TimerHub.builder()
            .userId(timer.getUserId())
            .startedAt(timer.getStartedAt())
            .totalSeconds(timer.getTotalSeconds())
            .remainedSeconds(timer.getRemainedSeconds())
            .emoji(timer.getEmoji())
            .timerStatus(timer.getTimerStatus())
            .build();

        timerRepository.save(timerhub);
    }

    public TimerRestDto getTimerHub(long timerId) {
        TimerHub timerHub = timerRepository.getById(timerId);

        return TimerRestDto.builder()
            .userId(timerHub.getUserId())
            .startedAt(timerHub.getStartedAt())
            .totalSeconds(timerHub.getTotalSeconds())
            .remainedSeconds(timerHub.getRemainedSeconds())
            .emoji(timerHub.getEmoji())
            .timerStatus(timerHub.getTimerStatus()).build();
    }
}
