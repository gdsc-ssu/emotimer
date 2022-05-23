package com.example.authservice.api.repository.user;

import com.example.authservice.api.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 데이터베이스에 이미 있는 이메일인지 체크하기 위한 메서드(즉, 이전에 가입한 사람인지 체크)
    User findByEmail(String email);
}
