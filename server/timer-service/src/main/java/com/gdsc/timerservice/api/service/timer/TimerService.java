package com.gdsc.timerservice.api.service.timer;

import com.gdsc.timerservice.api.dtos.TimerRestDto;
import com.gdsc.timerservice.api.entity.timer.TimerHub;
import com.gdsc.timerservice.api.repository.timer.TimerRepository;
import com.gdsc.timerservice.common.enums.TimerStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gdsc.timerservice.common.enums.TimerStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class TimerService {

    private final TimerRepository timerRepository;

    public TimerRestDto makeTimer(TimerRestDto timer) {

        TimerHub timerHub = timerRepository.findByUserId(timer.getUserId());
        if (timerHub != null) {
            timerHub.setTimerStatus(READY);
            return makeTimerDto(timerHub);
        }

        TimerHub timerhub = TimerHub.builder()
            .userId(timer.getUserId())
            .startedAt(timer.getStartedAt())
            .totalSeconds(timer.getTotalSeconds())
            .remainedSeconds(timer.getRemainedSeconds())
            .emoji(timer.getEmoji())
            .timerStatus(READY)
            .build();

        timerRepository.save(timerhub);

        return makeTimerDto(timerHub);
    }

    public TimerRestDto getTimerHub(long timerId) {
        TimerHub timerHub = timerRepository.getById(timerId);

        return makeTimerDto(timerHub);
    }

    private TimerRestDto makeTimerDto(TimerHub timerHub) {
        return TimerRestDto.builder()
            .userId(timerHub.getUserId())
            .startedAt(timerHub.getStartedAt())
            .totalSeconds(timerHub.getTotalSeconds())
            .remainedSeconds(timerHub.getRemainedSeconds())
            .emoji(timerHub.getEmoji())
            .timerStatus(timerHub.getTimerStatus()).build();
    }
}
