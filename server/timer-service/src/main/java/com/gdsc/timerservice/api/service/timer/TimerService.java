package com.gdsc.timerservice.api.service.timer;

import static com.gdsc.timerservice.common.enums.TimerStatus.PAUSED;
import static com.gdsc.timerservice.common.enums.TimerStatus.READY;
import static com.gdsc.timerservice.common.enums.TimerStatus.RUNNING;

import com.gdsc.timerservice.api.dtos.timer.request.PauseTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.request.ResetTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.request.SetTimerSettingsRequest;
import com.gdsc.timerservice.api.dtos.timer.request.StartTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.response.GetTimerResponse;
import com.gdsc.timerservice.api.dtos.timer.response.SetTimerSettingsResponse;
import com.gdsc.timerservice.api.entity.timer.Timer;
import com.gdsc.timerservice.api.repository.timer.TimerRepository;
import com.gdsc.timerservice.websocket.WebSocketTimerOperator;
import com.gdsc.timerservice.websocket.dto.WebSocketTimerOperation;
import com.gdsc.timerservice.websocket.enums.TimerOperation;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TimerService {

	private final TimerRepository timerRepository;
	private final WebSocketTimerOperator webSocketTimerOperator;

	public SetTimerSettingsResponse setTimerSettings(SetTimerSettingsRequest timer) {
		Timer timerHub;
		timerHub = timerRepository.findByUserId(timer.getUserId()).orElse(null);

		if (timerHub != null) {
			timerHub.setTimerId(UUID.randomUUID().toString());
			timerHub.setTotalTimeSeconds(timer.getTotalTime());
			timerHub.setCategory(timer.getCategory());
			timerHub.setTimerStatus(READY);
			return SetTimerSettingsResponse.builder()
				.startedAt(timerHub.getStartedAt())
				.totalTimeSeconds(timerHub.getTotalTimeSeconds())
				.remainedSeconds(timerHub.getRemainedSeconds())
				.category(timerHub.getCategory()).build();
		}

		timerHub = Timer.builder()
			.userId(timer.getUserId())
			.totalTimeSeconds(timer.getTotalTime())
			.category(timer.getCategory())
			.timerStatus(READY)
			.build();

		timerRepository.save(timerHub);

		return SetTimerSettingsResponse.builder()
			.startedAt(timerHub.getStartedAt())
			.totalTimeSeconds(timerHub.getTotalTimeSeconds())
			.remainedSeconds(timerHub.getRemainedSeconds())
			.category(timerHub.getCategory()).build();
	}

	public GetTimerResponse getTimer(long userId) {
		Timer timer = timerRepository.findByUserId(userId).orElse(null);
		return GetTimerResponse.builder().timer(timer).build();
	}

	//TODO TimerHistory 만들어서 저장하기

	public void startTimer(StartTimerRequest startTimerRequest) {
		Timer timer = timerRepository.findByUserId(startTimerRequest.getUserId()).orElseThrow(() -> new NoSuchElementException()); // Custom한 Exception을 만드는 것이 나을지 고민
		timer.setStartedAt(startTimerRequest.getStartTime());
		timer.setTimerStatus(RUNNING);

		WebSocketTimerOperation webSocketTimerOperation = WebSocketTimerOperation.builder()
			.timerOperation(TimerOperation.START)
			.userId(startTimerRequest.getUserId())
			.serverTime(startTimerRequest.getStartTime()).build();

		webSocketTimerOperator.operateTimer(webSocketTimerOperation);
	}

	public void pauseTimer(PauseTimerRequest pauseTimerRequest) {
		Timer timer = timerRepository.findByUserId(pauseTimerRequest.getUserId()).orElseThrow(() -> new NoSuchElementException()); // Custom한 Exception을 만드는 것이 나을지 고민
		long startTimeInEpoch = timer.getStartedAt().atZone(ZoneId.systemDefault()).toEpochSecond();
		long pausedTimeInEpoch = pauseTimerRequest.getPausedTime().atZone(ZoneId.systemDefault()).toEpochSecond();
		long remainedSeconds = timer.getTotalTimeSeconds() - (pausedTimeInEpoch - startTimeInEpoch);

		timer.setRemainedSeconds(remainedSeconds);
		timer.setTimerStatus(PAUSED);

		WebSocketTimerOperation webSocketTimerOperation = WebSocketTimerOperation.builder()
			.timerOperation(TimerOperation.PAUSE)
			.userId(pauseTimerRequest.getUserId())
			.serverTime(pauseTimerRequest.getPausedTime()).build();

		webSocketTimerOperator.operateTimer(webSocketTimerOperation);
	}

	public void resetTimer(ResetTimerRequest resetTimerRequest) {
		Timer timer = timerRepository.findByUserId(resetTimerRequest.getUserId()).orElseThrow(() -> new NoSuchElementException()); // Custom한 Exception을 만드는 것이 나을지 고민)
		timer.setStartedAt(null);
		timer.setRemainedSeconds(timer.getTotalTimeSeconds());
		timer.setTimerStatus(READY);

		WebSocketTimerOperation webSocketTimerOperation = WebSocketTimerOperation.builder()
			.timerOperation(TimerOperation.RESET)
			.userId(resetTimerRequest.getUserId()).build();

		webSocketTimerOperator.operateTimer(webSocketTimerOperation);
	}
}