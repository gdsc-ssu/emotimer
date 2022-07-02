package com.gdsc.timerservice.api.service.timer_task;

import com.gdsc.timerservice.api.dtos.timerhistory.request.CreateTimerHistoryRequest;
import com.gdsc.timerservice.api.service.timer.TimerHistoryService;
import com.gdsc.timerservice.api.service.timer_task.dto.CreateTimerTaskRequest;
import com.gdsc.timerservice.websocket.WebSocketTimerOperator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimerTaskScheduler {

	private WebSocketTimerOperator webSocketTimerOperator;

	private TimerHistoryService timerHistoryService;

	private Map<Long, TimerTask> timerTasks;

	public void createTimerTask(CreateTimerTaskRequest createTimerTaskRequest) {

		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				CreateTimerHistoryRequest createTimerHistoryRequest = CreateTimerHistoryRequest.builder()
					.userId(createTimerTaskRequest.getUserId())
					.totalSeconds(createTimerTaskRequest.getRemainedSeconds())
					.succeed(true)
					.category(createTimerTaskRequest.getCategory()).build();

				timerHistoryService.createTimerHistory(createTimerHistoryRequest);
			}
		};
		timer.schedule(timerTask, createTimerTaskRequest.getRemainedSeconds() * 1000);
	}

	public void deleteTimerTask(long userId) {
		timerTasks.remove(userId);
	}
}