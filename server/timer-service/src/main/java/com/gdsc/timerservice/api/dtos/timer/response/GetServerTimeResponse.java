package com.gdsc.timerservice.api.dtos.timer.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetServerTimeResponse {

	private LocalDateTime now;
}
