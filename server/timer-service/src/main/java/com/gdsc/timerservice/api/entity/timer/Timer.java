package com.gdsc.timerservice.api.entity.timer;

import com.gdsc.timerservice.common.enums.Emoji;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Timer {

	@Id
	@GeneratedValue
	@Column
	private String timerId;

	@Column
	private long userId;

	@Column
	private LocalDateTime startedAt;

	@Column
	private Long totalTimeSeconds;

	@Column
	private Long remainedSeconds;

	//TODO 유저 Setting 이후 Category로 변경
	@Column
	private Emoji category;

	@Column
	@Enumerated(EnumType.STRING)
	private TimerStatus timerStatus;

}
