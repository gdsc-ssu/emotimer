package com.gdsc.timerservice;

import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.config.properties.OAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {AppProperties.class, OAuthProperties.class})
public class TimerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimerServiceApplication.class, args);
	}
}
