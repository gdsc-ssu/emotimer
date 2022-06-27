package com.gdsc.timerservice.api.service.timer;

import com.gdsc.timerservice.api.dtos.timer.response.GetTimerStaticsResponse;
import org.springframework.stereotype.Service;

@Service
public class TimerHistoryService {

	public GetTimerStaticsResponse getTimerStatics(int year, Integer momth, Integer day) {
		// TODO 페이징 쿼리..? 보다는 sort쿼리가 맞는듯
		return null;

	}
}
