package com.gdsc.timerservice.api.repository.user;

import com.gdsc.timerservice.api.entity.user.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByEmail(String email); // 이메일로 식별할 것임
    UserRefreshToken findByEmailAndRefreshToken(String userId, String refreshToken);
}
