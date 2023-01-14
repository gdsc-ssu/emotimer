package com.gdsc.timerservice.websocket.dto.request;

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
public class WebSocketTimerOperationRequest {

	private String userId;

	private LocalDateTime serverTime;

	private TimerOperation timerOperation;
}
