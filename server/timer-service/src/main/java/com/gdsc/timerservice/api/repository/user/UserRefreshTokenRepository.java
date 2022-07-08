package com.gdsc.timerservice.api.repository.user;

import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.entity.user.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByUuidAndRefreshToken(String uuid, String refreshToken);

    default void saveNewRefreshToken(String uuid, String email, String refreshToken) {
        saveAndFlush(new UserRefreshToken(null, uuid, email, refreshToken));
    }

    Optional<UserRefreshToken> findByUuid(String uuid);
}
