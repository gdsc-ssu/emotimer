package com.gdsc.timerservice.api.service.timer_task;

import com.gdsc.timerservice.api.dtos.timerhistory.request.CreateTimerHistoryRequest;
import com.gdsc.timerservice.api.service.timer.TimerHistoryService;
import com.gdsc.timerservice.api.service.timer_task.dto.CreateTimerTask;
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

	public void createTimerTask(CreateTimerTask createTimerTask) {

		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				CreateTimerHistoryRequest createTimerHistoryRequest = CreateTimerHistoryRequest.builder()
					.userId(createTimerTask.getUserId())
					.totalSeconds(createTimerTask.getRemainedSeconds())
					.succeed(true)
					.category(createTimerTask.getCategory()).build();

				timerHistoryService.createTimerHistory(createTimerHistoryRequest);
			}
		};
		timer.schedule(timerTask, createTimerTask.getRemainedSeconds() * 1000);
	}

	public void deleteTimerTask(long userId) {
		timerTasks.remove(userId);
	}
}