package com.gdsc.timerservice.api.repository.user;

import com.gdsc.timerservice.api.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	// 데이터베이스에 이미 있는 이메일인지 체크하기 위한 메서드(즉, 이전에 가입한 사람인지 체크)
	Optional<User> findByEmail(String email);
}
