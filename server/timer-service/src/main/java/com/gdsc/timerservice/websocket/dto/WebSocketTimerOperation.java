package com.gdsc.timerservice.websocket.dto;

import com.gdsc.timerservice.websocket.enums.TimerOperation;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WebSocketTimerOperation {

	private long userId;

	private LocalDateTime serverTime;

	//milli
	private long remainedSeconds;

	private TimerOperation timerOperation;
}
