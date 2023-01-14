package com.gdsc.timerservice.api.repository.user;

import com.gdsc.timerservice.api.entity.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("JPA 에서 해당 id 가 없을 때 처리방법")
    void idNotExists(){
        Optional<User> byId = userRepository.findById(100L);
        User user = byId.orElseThrow(IllegalArgumentException::new);
    }
}