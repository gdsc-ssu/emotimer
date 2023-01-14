package com.gdsc.timerservice.api.repository.user;

import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.entity.user.UserSetting;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingRepository extends JpaRepository<UserSetting, Long> {

	Optional<UserSetting> findByUser(User user);
}
