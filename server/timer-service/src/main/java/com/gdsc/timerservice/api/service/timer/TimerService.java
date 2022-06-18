package com.gdsc.timerservice.api.service.timer;

import static com.gdsc.timerservice.common.enums.TimerStatus.READY;

import com.gdsc.timerservice.api.dtos.timer.request.OperateTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.request.SetTimerSettingsRequest;
import com.gdsc.timerservice.api.dtos.timer.response.GetTimerResponse;
import com.gdsc.timerservice.api.dtos.timer.response.SetTimerSettingsResponse;
import com.gdsc.timerservice.api.entity.timer.Timer;
import com.gdsc.timerservice.api.repository.timer.TimerRepository;
import com.gdsc.timerservice.websocket.WebSocketTimerOperator;
import com.gdsc.timerservice.websocket.dto.WebSocketTimerOperation;
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
			timerHub.setTotalSeconds(timer.getTotalTime());
			timerHub.setEmoji(timer.getEmoji());
			timerHub.setTimerStatus(READY);
			return SetTimerSettingsResponse.builder().timer(timerHub).build();
		}

		timerHub = Timer.builder()
			.userId(timer.getUserId())
			.totalSeconds(timer.getTotalTime())
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

	public void operateTimer(OperateTimerRequest operateTimerRequest) {
		WebSocketTimerOperation webSocketTimerOperation = WebSocketTimerOperation.builder()
			.timerOperation(operateTimerRequest.getTimerOperation())
			.userId(operateTimerRequest.getUserId())
			.serverTime(operateTimerRequest.getServerTime()).build();

		webSocketTimerOperator.operateTimer(webSocketTimerOperation);
	}
}