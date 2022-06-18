package com.gdsc.timerservice.api.service.timer;

import static com.gdsc.timerservice.common.enums.TimerStatus.READY;

import com.gdsc.timerservice.api.dtos.timer.request.PauseTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.request.SetTimerSettingsRequest;
import com.gdsc.timerservice.api.dtos.timer.request.StartTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.response.GetTimerResponse;
import com.gdsc.timerservice.api.dtos.timer.response.SetTimerSettingsResponse;
import com.gdsc.timerservice.api.entity.timer.Timer;
import com.gdsc.timerservice.api.repository.timer.TimerRepository;
import com.gdsc.timerservice.common.enums.TimerStatus;
import com.gdsc.timerservice.websocket.WebSocketTimerOperator;
import com.gdsc.timerservice.websocket.dto.WebSocketTimerOperation;
import com.gdsc.timerservice.websocket.enums.TimerOperation;
import java.time.ZoneId;
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
		timerHub = timerRepository.findByUserId(timer.getUserId());
		if (timerHub != null) {
			timerHub.setTotalTime(timer.getTotalTime());
			timerHub.setEmoji(timer.getEmoji());
			timerHub.setTimerStatus(READY);
			return SetTimerSettingsResponse.builder().timer(timerHub).build();
		}

		timerHub = Timer.builder()
			.userId(timer.getUserId())
			.totalTime(timer.getTotalTime())
			.emoji(timer.getEmoji())
			.timerStatus(READY)
			.build();

		timerRepository.save(timerHub);

		return SetTimerSettingsResponse.builder().timer(timerHub).build();
	}

	public GetTimerResponse getTimer(long userId) {
		Timer timer = timerRepository.findByUserId(userId);
		return GetTimerResponse.builder().timer(timer).build();
	}

	/**
	 * 1. Timer start, pause, reset 따로 정의하기 2. Timer start, pause reset 별로 db 데이터 변경해야함 3. Timer
	 * update함수 만들고 웹소켓으로 모든 기기들에게 전송하기
	 */

	public void startTimer(StartTimerRequest startTimerRequest) {
		Timer timer = timerRepository.findByUserId(startTimerRequest.getUserId());
		timer.setStartedAt(startTimerRequest.getServerTime());
		timer.setTimerStatus(TimerStatus.RUNNING);

		WebSocketTimerOperation webSocketTimerOperation = WebSocketTimerOperation.builder()
			.timerOperation(TimerOperation.START)
			.userId(startTimerRequest.getUserId())
			.serverTime(startTimerRequest.getServerTime()).build();

		webSocketTimerOperator.operateTimer(webSocketTimerOperation);
	}

	public void pauseTimer(PauseTimerRequest pauseTimerRequest) {
		Timer timer = timerRepository.findByUserId(pauseTimerRequest.getUserId());
		long startTimeMilli = timer.getStartedAt().atZone(ZoneId.systemDefault()).toEpochSecond();
		long pausedTimeMilli = pauseTimerRequest.getPausedTime().atZone(ZoneId.systemDefault())
			.toEpochSecond();
		long remainedTime = timer.getTotalTime() - pausedTimeMilli - startTimeMilli;

		timer.setRemainedSeconds(remainedTime);
		timer.setTimerStatus(TimerStatus.PAUSED);

		WebSocketTimerOperation webSocketTimerOperation = WebSocketTimerOperation.builder()
			.timerOperation(TimerOperation.PAUSE)
			.userId(pauseTimerRequest.getUserId())
			.remainedSeconds(remainedTime).build();

		webSocketTimerOperator.operateTimer(webSocketTimerOperation);
	}

//	public void operateTimer(OperateTimerRequest operateTimerRequest) {
//		WebSocketTimerOperation webSocketTimerOperation = WebSocketTimerOperation.builder()
//			.timerOperation(operateTimerRequest.getTimerOperation())
//			.userId(operateTimerRequest.getUserId())
//			.serverTime(operateTimerRequest.getServerTime()).build();
//
//		webSocketTimerOperator.operateTimer(webSocketTimerOperation);
//	}
}