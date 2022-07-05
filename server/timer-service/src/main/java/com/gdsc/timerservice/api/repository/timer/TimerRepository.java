package com.gdsc.timerservice.api.repository.timer;

import com.gdsc.timerservice.api.entity.timer.Timer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<Timer, Long> {

	Optional<Timer> findByUserId(long userId);
}
