package com.gdsc.timerservice.api.entity.timer;

import com.gdsc.timerservice.common.enums.Emoji;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class TimerHistory {

	@Id
	@GeneratedValue
	@Column
	private long timerHistoryId;

	@Column
	private long userId;

	@Column
	private LocalDateTime dateTime;

	@Column
	private int year;

	@Column
	private int month;

	@Column
	private int day;

	// 여기서의 total seconds는 timer가 실제 동작한 시간이다.
	@Column
	private long totalSeconds;

	@Column
	private boolean succeed;

	@Column
	private Emoji category; // 서연님 user Settings 끝나면 Emoji -> Category로 변경
}