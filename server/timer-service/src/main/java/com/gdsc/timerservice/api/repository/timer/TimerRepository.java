package com.gdsc.timerservice.api.repository.timer;

import com.gdsc.timerservice.api.entity.timer.TimerHub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<TimerHub, Long> {


}
