package com.gdsc.timerservice.api.entity.timer;

import com.gdsc.timerservice.common.enums.Emoji;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

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
	private String userId;

	@Column
	private LocalDateTime dateTime;

	@Column
	private int year;

	@Column
	private int month;

	// '월별 통계'를 위한 컬럼. 해당 날짜가 어떤 월로 포함되는지를 나타냄
	// ex) 1월1일은 1월이지만 '월별 통계'에서는 12월의 마지막주차에 포함된다.
	@Comment("월별 통계를 할 때 해당 날짜가 어떤 월로 포함되는지를 나타냄")
	@Column
	private int monthForStatistics;

	// '월별 통계'를 위한 컬럼. 해당 월의 몇주차인지 나타냄
	@Comment("월별 통계를 할 때 해당 월의 몇주차인지 나타냄")
	@Column
	private int week;

	@Column
	private int day;

	@Column
	private long spentSeconds;

	@Column
	private boolean succeed;

	@Column
	@Enumerated(EnumType.STRING)
	private Emoji emoji;
}