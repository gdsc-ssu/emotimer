package com.gdsc.timerservice.websocket.dto.response;

import com.gdsc.timerservice.websocket.enums.TimerOperation;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WebSocketTimerOperationResponse {

	private LocalDateTime serverTime;

	private TimerOperation timerOperation;

}
