package com.gdsc.timerservice.api.repository.timer;


import static com.gdsc.timerservice.api.entity.timer.QTimerHistory.timerHistory;

import com.gdsc.timerservice.api.dtos.timerhistory.EmojiAndTotalTime;
import com.gdsc.timerservice.api.dtos.timerhistory.TimerStatistics;
import com.gdsc.timerservice.api.dtos.timerhistory.queryprojection.QTimerStatisticsOfMonthQueryResult;
import com.gdsc.timerservice.api.dtos.timerhistory.queryprojection.QTimerStatisticsOfWeekQueryResult;
import com.gdsc.timerservice.api.dtos.timerhistory.queryprojection.QTimerStatisticsOfYearQueryResult;
import com.gdsc.timerservice.api.dtos.timerhistory.queryprojection.TimerStatisticsOfMonthQueryResult;
import com.gdsc.timerservice.api.dtos.timerhistory.queryprojection.TimerStatisticsOfWeekQueryResult;
import com.gdsc.timerservice.api.dtos.timerhistory.queryprojection.TimerStatisticsOfYearQueryResult;
import com.gdsc.timerservice.api.dtos.timerhistory.queryprojection.TimerStatisticsQueryResult;
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
	public List<TimerStatistics> getTimerStatistics(String userId, int year) {

		List<TimerStatisticsOfYearQueryResult> queryResult = queryFactory
			.select(new QTimerStatisticsOfYearQueryResult(timerHistory.month, timerHistory.emoji, timerHistory.spentSeconds.sum()))
			.from(timerHistory)
			.where(timerHistory.year.eq(year).and(timerHistory.userId.eq(userId)))
			.groupBy(timerHistory.emoji, timerHistory.month)
			.fetch();

		List<TimerStatistics> timerStatistics = new ArrayList<>();

		queryResult.stream().collect(Collectors.groupingBy(TimerStatisticsOfYearQueryResult::getMonth))
			.forEach((month, queryResults) -> timerStatistics.add(TimerStatistics.builder().month(month).emojiAndTotalTimeList(makeTimerStatisticsList(queryResults)).build()));

		return timerStatistics;
	}

	@Override
	public List<TimerStatistics> getTimerStatistics(String userId, int year, int month) {

		List<TimerStatisticsOfMonthQueryResult> queryResult = queryFactory
			.select(new QTimerStatisticsOfMonthQueryResult(timerHistory.week, timerHistory.emoji, timerHistory.spentSeconds.sum()))
			.from(timerHistory)
			.where(timerHistory.year.eq(year).and(timerHistory.monthForStatistics.eq(month)).and(timerHistory.userId.eq(userId)))
			.groupBy(timerHistory.emoji, timerHistory.week)
			.fetch();

		List<TimerStatistics> statisticsList = new ArrayList<>();

		queryResult.stream().collect(Collectors.groupingBy(TimerStatisticsOfMonthQueryResult::getWeek))
			.forEach((week, queryResults) -> statisticsList.add(TimerStatistics.builder().week(week).emojiAndTotalTimeList(makeTimerStatisticsList(queryResults)).build()));

		return statisticsList;
	}

	@Override
	public List<TimerStatistics> getTimerStatistics(String userId, int year, int month, int week) {

		List<TimerStatisticsOfWeekQueryResult> queryResult = queryFactory
			.select(new QTimerStatisticsOfWeekQueryResult(timerHistory.day, timerHistory.emoji, timerHistory.spentSeconds.sum()))
			.from(timerHistory)
			.where(timerHistory.year.eq(year).and(timerHistory.monthForStatistics.eq(month)).and(timerHistory.week.eq(week)).and(timerHistory.userId.eq(userId)))
			.groupBy(timerHistory.emoji, timerHistory.day)
			.fetch();

		List<TimerStatistics> statisticsList = new ArrayList<>();

		queryResult.stream().collect(Collectors.groupingBy(TimerStatisticsOfWeekQueryResult::getDay))
			.forEach((day, queryResults) -> statisticsList.add(TimerStatistics.builder().day(day).emojiAndTotalTimeList(makeTimerStatisticsList(queryResults)).build()));

		return statisticsList;
	}

	private <T extends TimerStatisticsQueryResult> List<EmojiAndTotalTime> makeTimerStatisticsList(List<T> list) {
		return list.stream().map(each -> new EmojiAndTotalTime(each.getEmoji(), each.getTotalSeconds())).collect(Collectors.toList());
	}

	/**
	 * 아래부터는 나중에 사용할 수도 있는 메서드라서 남겨놓음
	 */
//	private BooleanBuilder allEqual(int year, Integer month, Integer day) {
//		return yearEqual(year).and(monthEqual(month)).and(dayEqual(day));
//	}
//
//	private BooleanBuilder yearEqual(int year) {
//		return new BooleanBuilder(timerHistory.year.eq(year));
//	}
//
//	private BooleanBuilder monthEqual(Integer month) {
//		if (month == null) {
//			return new BooleanBuilder();
//		}
//		return new BooleanBuilder(timerHistory.month.eq(month));
//	}
//
//	private BooleanBuilder dayEqual(Integer day) {
//		if (day == null) {
//			return new BooleanBuilder();
//		}
//		return new BooleanBuilder(timerHistory.day.eq(day));
//	}
}