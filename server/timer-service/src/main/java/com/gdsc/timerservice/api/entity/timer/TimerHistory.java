package com.gdsc.timerservice.api.entity.timer;

import javax.persistence.*;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long timerHistoryId;

	@Column
	private long userId;
}