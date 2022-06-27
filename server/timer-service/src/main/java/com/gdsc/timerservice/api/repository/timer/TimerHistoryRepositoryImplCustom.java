package com.gdsc.timerservice.api.repository.timer;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TimerHistoryRepositoryImplCustom implements TimerHistoryRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public TimerHistoryRepositoryImplCustom(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}
}
