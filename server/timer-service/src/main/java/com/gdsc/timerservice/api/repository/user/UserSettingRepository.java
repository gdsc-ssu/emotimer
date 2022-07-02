package com.gdsc.timerservice.api.repository.user;

import com.gdsc.timerservice.api.entity.user.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingRepository extends JpaRepository<UserSetting, Long> {
}
