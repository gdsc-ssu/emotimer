package com.gdsc.timerservice.api.repository.user;

import com.gdsc.timerservice.api.entity.user.User;
import com.gdsc.timerservice.api.entity.user.UserRefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {

	UserRefreshToken findByIdAndRefreshToken(Long id, String refreshToken);

	Optional<UserRefreshToken> findByUser(User user);

	default void saveNewRefreshToken(String id, String email, String refreshToken) {
		saveAndFlush(new UserRefreshToken(null, new User(id), email, refreshToken));
	}

}
