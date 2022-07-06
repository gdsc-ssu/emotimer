package com.gdsc.timerservice.api.service.timer;

import static com.gdsc.timerservice.api.entity.timer.TimerStatus.PAUSED;
import static com.gdsc.timerservice.api.entity.timer.TimerStatus.READY;
import static com.gdsc.timerservice.api.entity.timer.TimerStatus.RUNNING;

import com.gdsc.timerservice.api.dtos.timer.request.PauseTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.request.ResetTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.request.ResumeTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.request.SetTimerSettingsRequest;
import com.gdsc.timerservice.api.dtos.timer.request.StartTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.response.GetTimerResponse;
import com.gdsc.timerservice.api.dtos.timer.response.SetTimerSettingsResponse;
import com.gdsc.timerservice.api.dtos.timerhistory.request.CreateTimerHistoryRequest;
import com.gdsc.timerservice.api.entity.timer.Timer;
import com.gdsc.timerservice.api.repository.timer.TimerRepository;
import com.gdsc.timerservice.api.service.timer_task.TimerTaskScheduler;
import com.gdsc.timerservice.api.service.timer_task.dto.CreateTimerTaskRequest;
import com.gdsc.timerservice.websocket.WebSocketTimerOperator;
import com.gdsc.timerservice.websocket.dto.request.WebSocketChangeTimerSettingsRequest;
import com.gdsc.timerservice.websocket.dto.request.WebSocketTimerOperationRequest;
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

	private final TimerTaskScheduler timerTaskScheduler;

	private final TimerHistoryService timerHistoryService;

	public SetTimerSettingsResponse setTimerSettings(SetTimerSettingsRequest setTimerSettingsRequest) {
		Timer timer;
		timer = timerRepository.findByUserId(setTimerSettingsRequest.getUserId()).orElse(null);

		if (timer != null) {
			timer.setTotalTimeSeconds(setTimerSettingsRequest.getTotalTime());
			timer.setRemainedSeconds(setTimerSettingsRequest.getTotalTime());
			timer.setEmoji(setTimerSettingsRequest.getEmoji());
			timer.setTimerStatus(READY);

			WebSocketChangeTimerSettingsRequest changeTimerSettingsRequest = WebSocketChangeTimerSettingsRequest.builder()
				.userId(setTimerSettingsRequest.getUserId())
				.totalTimeSeconds(setTimerSettingsRequest.getTotalTime())
				.emoji(setTimerSettingsRequest.getEmoji()).build();

			webSocketTimerOperator.changeTimerSetting(changeTimerSettingsRequest);

			return SetTimerSettingsResponse.builder()
				.startedAt(timer.getStartedAt())
				.totalTimeSeconds(timer.getTotalTimeSeconds())
				.remainedSeconds(timer.getRemainedSeconds())
				.emoji(timer.getEmoji()).build();
		}

		timer = Timer.builder()
			.timerId(UUID.randomUUID().toString())
			.userId(setTimerSettingsRequest.getUserId())
			.totalTimeSeconds(setTimerSettingsRequest.getTotalTime())
			.remainedSeconds(setTimerSettingsRequest.getTotalTime())
			.emoji(setTimerSettingsRequest.getEmoji())
			.timerStatus(READY)
			.build();
		timerRepository.save(timer);

		return SetTimerSettingsResponse.builder()
			.startedAt(timer.getStartedAt())
			.totalTimeSeconds(timer.getTotalTimeSeconds())
			.remainedSeconds(timer.getRemainedSeconds())
			.emoji(timer.getEmoji()).build();
	}

	public GetTimerResponse getTimer(String userId) {
		Timer timer = timerRepository.findByUserId(userId).orElse(null);
		return GetTimerResponse.builder()
			.startedAt(timer.getStartedAt())
			.totalTimeSeconds(timer.getTotalTimeSeconds())
			.remainedSeconds(timer.getRemainedSeconds())
			.emoji(timer.getEmoji())
			.timerStatus(timer.getTimerStatus()).build();
	}

	public void startTimer(StartTimerRequest startTimerRequest) {
		Timer timer = timerRepository.findByUserId(startTimerRequest.getUserId()).orElseThrow(() -> new NoSuchElementException()); // Custom한 Exception을 만드는 것이 나을지 고민
		timer.setStartedAt(startTimerRequest.getStartTime());
		timer.setTimerStatus(RUNNING);

		WebSocketTimerOperationRequest webSocketTimerOperationRequest = WebSocketTimerOperationRequest.builder()
			.timerOperation(TimerOperation.START)
			.userId(startTimerRequest.getUserId())
			.serverTime(startTimerRequest.getStartTime()).build();

		webSocketTimerOperator.operateTimer(webSocketTimerOperationRequest);

		CreateTimerTaskRequest createTimerTaskRequest = CreateTimerTaskRequest.builder()
			.userId(startTimerRequest.getUserId())
			.totalTimeSeconds(timer.getTotalTimeSeconds())
			.remainedSeconds(timer.getRemainedSeconds())
			.emoji(timer.getEmoji()).build();

		timerTaskScheduler.createSuccessTimerTask(createTimerTaskRequest);
	}

	public void pauseTimer(PauseTimerRequest pauseTimerRequest) {
		Timer timer = timerRepository.findByUserId(pauseTimerRequest.getUserId()).orElseThrow(() -> new NoSuchElementException()); // Custom한 Exception을 만드는 것이 나을지 고민
		long startTimeInEpoch = timer.getStartedAt().atZone(ZoneId.systemDefault()).toEpochSecond();
		long pausedTimeInEpoch = pauseTimerRequest.getPausedTime().atZone(ZoneId.systemDefault()).toEpochSecond();
		long remainedSeconds = timer.getTotalTimeSeconds() - (pausedTimeInEpoch - startTimeInEpoch);

		timer.setRemainedSeconds(remainedSeconds);
		timer.setTimerStatus(PAUSED);

		WebSocketTimerOperationRequest webSocketTimerOperationRequest = WebSocketTimerOperationRequest.builder()
			.timerOperation(TimerOperation.PAUSE)
			.userId(pauseTimerRequest.getUserId())
			.serverTime(pauseTimerRequest.getPausedTime()).build();

		webSocketTimerOperator.operateTimer(webSocketTimerOperationRequest);

		timerTaskScheduler.deleteTimerTask(pauseTimerRequest.getUserId());
	}

	public void resumeTimer(ResumeTimerRequest resumeTimerRequest) {
		Timer timer = timerRepository.findByUserId(resumeTimerRequest.getUserId()).orElseThrow(() -> new NoSuchElementException()); // Custom한 Exception을 만드는 것이 나을지 고민
		timer.setStartedAt(resumeTimerRequest.getResumeTime());
		timer.setTimerStatus(RUNNING);

		WebSocketTimerOperationRequest webSocketTimerOperationRequest = WebSocketTimerOperationRequest.builder()
			.timerOperation(TimerOperation.RESUME)
			.userId(resumeTimerRequest.getUserId())
			.serverTime(resumeTimerRequest.getResumeTime()).build();

		webSocketTimerOperator.operateTimer(webSocketTimerOperationRequest);

		CreateTimerTaskRequest createTimerTaskRequest = CreateTimerTaskRequest.builder()
			.userId(resumeTimerRequest.getUserId())
			.totalTimeSeconds(timer.getTotalTimeSeconds())
			.remainedSeconds(timer.getRemainedSeconds())
			.emoji(timer.getEmoji()).build();

		timerTaskScheduler.createSuccessTimerTask(createTimerTaskRequest);
	}

	public void resetTimer(ResetTimerRequest resetTimerRequest) {
		Timer timer = timerRepository.findByUserId(resetTimerRequest.getUserId()).orElseThrow(() -> new NoSuchElementException()); // Custom한 Exception을 만드는 것이 나을지 고민)
		timer.setStartedAt(null);
		timer.setRemainedSeconds(timer.getTotalTimeSeconds());
		timer.setTimerStatus(READY);

		WebSocketTimerOperationRequest webSocketTimerOperationRequest = WebSocketTimerOperationRequest.builder()
			.timerOperation(TimerOperation.RESET)
			.userId(resetTimerRequest.getUserId()).build();

		webSocketTimerOperator.operateTimer(webSocketTimerOperationRequest);

		timerTaskScheduler.deleteTimerTask(resetTimerRequest.getUserId());

		CreateTimerHistoryRequest createTimerHistoryRequest = CreateTimerHistoryRequest.builder()
			.userId(resetTimerRequest.getUserId())
			.spentTime(timer.getTotalTimeSeconds() - resetTimerRequest.getRemainedSeconds())
			.succeed(false)
			.emoji(timer.getEmoji()).build();

		timerHistoryService.createTimerHistory(createTimerHistoryRequest);
	}
}