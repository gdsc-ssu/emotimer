package com.gdsc.timerservice.api.repository.timer;

import static com.gdsc.timerservice.api.entity.timer.QTimerHistory.timerHistory;

import com.gdsc.timerservice.api.dtos.timerhistory.TimerStatistics;
import com.gdsc.timerservice.api.dtos.timerhistory.TimerStatisticsOfYear;
import com.gdsc.timerservice.api.dtos.timerhistory.queryprojection.QQueryForTimerStatisticsOfYear;
import com.gdsc.timerservice.api.dtos.timerhistory.queryprojection.QueryForTimerStatisticsOfYear;
import com.gdsc.timerservice.api.dtos.timerhistory.response.GetTimerStatisticsOfYearResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class TimerHistoryRepositoryImpl implements TimerHistoryRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public TimerHistoryRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public GetTimerStatisticsOfYearResponse getTimerStatisticsOfYear(String userId, int year) {

		List<TimerStatisticsOfYear> timerStatisticsOfYear = new ArrayList<>();

		List<QueryForTimerStatisticsOfYear> queryResult = queryFactory
			.select(new QQueryForTimerStatisticsOfYear(timerHistory.month, timerHistory.emoji, timerHistory.spentSeconds.sum()))
			.from(timerHistory)
			.where(timerHistory.year.eq(year).and(timerHistory.userId.eq(userId)))
			.groupBy(timerHistory.emoji, timerHistory.month)
			.orderBy(timerHistory.month.desc(), timerHistory.spentSeconds.sum().desc())
			.fetch();

		queryResult.stream().collect(Collectors.groupingBy(QueryForTimerStatisticsOfYear::getMonth))
			.forEach((month, queryResults) -> timerStatisticsOfYear.add(new TimerStatisticsOfYear(month, makeTimerStatisticsList(queryResults))));

		GetTimerStatisticsOfYearResponse response = new GetTimerStatisticsOfYearResponse(timerStatisticsOfYear);
		return response;
	}

	private List<TimerStatistics> makeTimerStatisticsList(List<QueryForTimerStatisticsOfYear> list) {
		List<TimerStatistics> statisticsList = new ArrayList<>();
		list.stream().forEach(
			each -> {
				statisticsList.add(new TimerStatistics(each.getEmoji(), each.getTotalSeconds()));
			}
		);
		return statisticsList;
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
}