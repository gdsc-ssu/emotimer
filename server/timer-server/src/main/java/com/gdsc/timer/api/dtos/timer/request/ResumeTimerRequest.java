package com.gdsc.timerservice.api.dtos.timer.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResumeTimerRequest {

	private LocalDateTime resumeTime;
}
