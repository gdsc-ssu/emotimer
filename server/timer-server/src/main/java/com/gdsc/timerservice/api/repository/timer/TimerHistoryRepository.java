package com.gdsc.timerservice.api.repository.timer;

import com.gdsc.timerservice.api.entity.timer.TimerHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimerHistoryRepository extends JpaRepository<TimerHistory, Long>, TimerHistoryRepositoryCustom {

}
