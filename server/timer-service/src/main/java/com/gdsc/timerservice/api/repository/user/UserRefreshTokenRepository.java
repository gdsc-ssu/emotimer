package com.gdsc.timerservice.api.repository.user;

import com.gdsc.timerservice.api.entity.user.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByIdAndRefreshToken(Long id, String refreshToken);

    default void saveNewRefreshToken(Long id, String email, String refreshToken) {
        saveAndFlush(new UserRefreshToken(id, email, refreshToken));
    }

}
