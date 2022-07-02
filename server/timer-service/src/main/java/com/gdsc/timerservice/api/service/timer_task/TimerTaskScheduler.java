package com.gdsc.timerservice.api.service.timer_task;

import static com.gdsc.timerservice.api.entity.timer.TimerStatus.FINISHED;

import com.gdsc.timerservice.api.dtos.timerhistory.request.CreateTimerHistoryRequest;
import com.gdsc.timerservice.api.repository.timer.TimerRepository;
import com.gdsc.timerservice.api.service.timer.TimerHistoryService;
import com.gdsc.timerservice.api.service.timer_task.dto.CreateTimerTaskRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimerTaskScheduler {

	private final TimerHistoryService timerHistoryService;

	private final TimerRepository timerRepository;

	private Map<String, TimerTask> timerTasks = new HashMap<>();

	//Entity의 timer 아님. Java에서 제공하는 Util
	private Timer timer = new Timer();

	public void createSuccessTimerTask(CreateTimerTaskRequest createTimerTaskRequest) {

		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				CreateTimerHistoryRequest createTimerHistoryRequest = CreateTimerHistoryRequest.builder()
					.userId(createTimerTaskRequest.getUserId())
					.spentTime(createTimerTaskRequest.getTotalTimeSeconds())
					.succeed(true)
					.category(createTimerTaskRequest.getCategory()).build();

				timerHistoryService.createTimerHistory(createTimerHistoryRequest);

				com.gdsc.timerservice.api.entity.timer.Timer timer = timerRepository.findByUserId(createTimerTaskRequest.getUserId()).orElseThrow(() -> new NoSuchElementException());
				timer.setTimerStatus(FINISHED);
				timerRepository.save(timer);

				timerTasks.remove(createTimerTaskRequest.getUserId());
			}
		};

		timer.schedule(timerTask, createTimerTaskRequest.getRemainedSeconds() * 1000);
		timerTasks.put(createTimerTaskRequest.getUserId(), timerTask);
	}

	public void deleteTimerTask(String userId) {
		TimerTask timerTask = timerTasks.get(userId);
		if (timerTask != null) {
			timerTask.cancel();
		}
		timerTasks.remove(userId);
	}
}