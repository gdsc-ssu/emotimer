package com.gdsc.timerservice.api.service.timer;

import com.gdsc.timerservice.api.dtos.timerhistory.WeekOfMonth;
import com.gdsc.timerservice.api.dtos.timerhistory.request.CreateTimerHistoryRequest;
import com.gdsc.timerservice.api.dtos.timerhistory.response.GetTimerStatisticsOfYearResponse;
import com.gdsc.timerservice.api.entity.timer.TimerHistory;
import com.gdsc.timerservice.api.repository.timer.TimerHistoryRepository;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimerHistoryService {

	private final TimerHistoryRepository timerHistoryRepository;

	public GetTimerStatisticsOfYearResponse getTimerStatisticsOfYear(String userId, int year) {
		return timerHistoryRepository.getTimerStatisticsOfYear(userId, year);
	}

	public void createTimerHistory(CreateTimerHistoryRequest createTimerHistoryRequest) {
		LocalDateTime now = LocalDateTime.now();

		int year = now.getYear();
		int month = now.getMonth().getValue();
		int day = now.getDayOfMonth();

		WeekOfMonth weekOfMonth = getCurrentWeekOfMonth(year, month, day);

		TimerHistory timerHistory = TimerHistory.builder()
			.userId(createTimerHistoryRequest.getUserId())
			.dateTime(now)
			.year(year)
			.month(month)
			.monthForStatistics(weekOfMonth.getMonth())
			.week(weekOfMonth.getWeek())
			.day(day)
			.spentSeconds(createTimerHistoryRequest.getSpentTime())
			.succeed(createTimerHistoryRequest.isSucceed())
			.emoji(createTimerHistoryRequest.getEmoji()).build();

		timerHistoryRepository.save(timerHistory);
	}

	/**
	 * 파라미터로 전달 된 날짜의 1일의 주차 계산 1일이 목요일(5) 보다 클 경우 첫째 주 이므로 0을 반환 1일이 월 ~ 목 이외의 날짜 일 경우 -1 을 반환. 1일이 목요일(5) 보다 작으면 첫째 주가 아니므로 1을 반환
	 */
	public static int subWeekNumberIsFirstDayAfterThursday(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance(Locale.KOREA);
		calendar.set(year, month - 1, day);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);

		int weekOfDay = calendar.get(Calendar.DAY_OF_WEEK);

		if ((weekOfDay >= Calendar.MONDAY) && (weekOfDay <= Calendar.THURSDAY)) {
			return 0;
		} else if (day == 1 && (weekOfDay < Calendar.MONDAY || weekOfDay > Calendar.TUESDAY)) {
			return -1;
		} else {
			return 1;
		}
	}

	/**
	 * 해당 날짜가 마지막 주에 해당하고 마지막주의 마지막날짜가 목요일보다 작으면 다음달로 넘겨야 한다 +1 목요일보다 크거나 같을 경우 이번달로 결정한다 +0
	 */
	public static int addMonthIsLastDayBeforeThursday(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance(Locale.KOREA);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(year, month - 1, day);

		int currentWeekNumber = calendar.get(Calendar.WEEK_OF_MONTH);
		int maximumWeekNumber = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);

		if (currentWeekNumber == maximumWeekNumber) {
			calendar.set(year, month - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			int maximumDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

			if (maximumDayOfWeek < Calendar.THURSDAY && maximumDayOfWeek > Calendar.SUNDAY) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public static WeekOfMonth getCurrentWeekOfMonth(int year, int month, int day) {
		int subtractFirstWeekNumber = subWeekNumberIsFirstDayAfterThursday(year, month, day);
		int subtractLastWeekNumber = addMonthIsLastDayBeforeThursday(year, month, day);

		// 마지막 주차에서 다음 달로 넘어갈 경우 다음달의 1일을 기준으로 정해준다.
		// 추가로 다음 달 첫째주는 목요일부터 시작하는 과반수의 일자를 포함하기 때문에 한주를 빼지 않는다.
		if (subtractLastWeekNumber > 0) {
			day = 1;
			subtractFirstWeekNumber = 0;
		}

		if (subtractFirstWeekNumber < 0) {
			Calendar calendar = Calendar.getInstance(Locale.KOREA);
			calendar.set(year, month - 1, day);
			calendar.add(Calendar.DATE, -1);

			return getCurrentWeekOfMonth(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE));
		}

		Calendar calendar = Calendar.getInstance(Locale.KOREA);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(1);
		calendar.set(year, month - (1 - subtractLastWeekNumber), day);

		int dayOfWeekForFirstDayOfMonth = calendar.get(Calendar.WEEK_OF_MONTH) - subtractFirstWeekNumber;

		return WeekOfMonth.builder().month(calendar.get(Calendar.MONTH) + 1).week(dayOfWeekForFirstDayOfMonth).build();
	}
}