package com.gdsc.timerservice.api.entity.timer;

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
}