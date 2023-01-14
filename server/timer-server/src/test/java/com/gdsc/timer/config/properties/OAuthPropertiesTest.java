package com.gdsc.timerservice.config.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OAuthPropertiesTest {
    @Autowired
    OAuthProperties oAuthProperties;
    @Test
    void test() {
        System.out.println(oAuthProperties.getKakaoClientId());
    }

}