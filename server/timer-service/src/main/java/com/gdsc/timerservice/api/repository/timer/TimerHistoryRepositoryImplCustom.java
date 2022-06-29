package com.gdsc.timerservice.api.repository.timer;

import com.gdsc.timerservice.api.dtos.timer.response.GetTimerStatisticsResponse;
import com.gdsc.timerservice.api.dtos.timer.response.QTimerStatistics;
import com.gdsc.timerservice.api.dtos.timer.response.TimerStatistics;
import com.gdsc.timerservice.api.entity.timer.QTimerHistory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TimerHistoryRepositoryImplCustom implements TimerHistoryRepositoryCustom {

	QTimerHistory timerHistory = QTimerHistory.timerHistory;
	private final JPAQueryFactory queryFactory;

	public TimerHistoryRepositoryImplCustom(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	public GetTimerStatisticsResponse getTimerStatistics(int year, Integer month, Integer day) {

		GetTimerStatisticsResponse response = new GetTimerStatisticsResponse();

		List<TimerStatistics> timerStatisticsList = queryFactory
			.select(new QTimerStatistics(timerHistory.category, timerHistory.totalSeconds))
			.where(allEqual(year, month, day))
			.orderBy(timerHistory.totalSeconds.desc())
			.groupBy(getConditionBy(month, day))
			.fetch();

		response.setTimerStatisticsList(timerStatisticsList);

		return response;
	}

	private BooleanBuilder allEqual(int year, Integer month, Integer day) {
		return yearEqual(year).and(monthEqual(month)).and(dayEqual(day));
	}

	private BooleanBuilder yearEqual(int year) {
		return new BooleanBuilder(timerHistory.year.eq(year));
	}

	private BooleanBuilder monthEqual(Integer month) {
		if (month == null) {
			return new BooleanBuilder();
		}
		return new BooleanBuilder(timerHistory.month.eq(month));
	}

	private BooleanBuilder dayEqual(Integer day) {
		if (day == null) {
			return new BooleanBuilder();
		}
		return new BooleanBuilder(timerHistory.day.eq(day));
	}

	// 더 좋은 방법은 없을까....
	private Expression<?>[] getConditionBy(Integer month, Integer day) {
		if (month == null && day == null) {
			return new Expression[]{timerHistory.year};
		}
		if (month != null && day == null) {
			return new Expression[]{timerHistory.year, timerHistory.month};
		}
		return new Expression[]{timerHistory.year, timerHistory.month, timerHistory.day};
	}
}