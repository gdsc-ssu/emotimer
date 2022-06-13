package com.gdsc.timerservice.api.service.timer;

import static com.gdsc.timerservice.common.enums.TimerStatus.READY;

import com.gdsc.timerservice.api.dtos.timer.request.MakeTimerRequest;
import com.gdsc.timerservice.api.dtos.timer.response.CreateTimerResponse;
import com.gdsc.timerservice.api.dtos.timer.response.GetTimerResponse;
import com.gdsc.timerservice.api.entity.timer.TimerHub;
import com.gdsc.timerservice.api.repository.timer.TimerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TimerService {

	private final TimerRepository timerRepository;

	public CreateTimerResponse createTimer(MakeTimerRequest timer) {

		TimerHub timerHub;
		timerHub = timerRepository.findByUserId(timer.getUserId());
		if (timerHub != null) {
			timerHub.setTimerStatus(READY);
			return CreateTimerResponse.builder().timerHub(timerHub).build();
		}

		timerHub = TimerHub.builder()
			.userId(timer.getUserId())
			.startedAt(timer.getStartedAt())
			.totalSeconds(timer.getTotalSeconds())
			.remainedSeconds(timer.getRemainedSeconds())
			.emoji(timer.getEmoji())
			.timerStatus(READY)
			.build();

		timerRepository.save(timerHub);

		return CreateTimerResponse.builder().timerHub(timerHub).build();
	}

	public GetTimerResponse getTimerHub(long timerId) {
		TimerHub timerHub = timerRepository.getById(timerId);
		return GetTimerResponse.builder().timerHub(timerHub).build();
	}

//    public void operateTimer(OperateTimerRequest operateTimerRequest) {
//        WebSocketTimerOperation webSocketTimerOperation = WebSocketTimerOperation.builder()
//                .timerOperation(operateTimerRequest.getTimerOperation())
//                .userId(operateTimerRequest.getUserId())
//                .serverTime(operateTimerRequest.getServerTime()).build();
//
//        // TODO 웹소켓으로 각 서버에 요청 보내기
//
//        // START, RESET, PAUSE에 따라 DB의 timerHub 정보 변경하기
//        new TimerOperator().operateTimer(webSocketTimerOperation);
//    }
}