package com.gdsc.timerservice;

import com.gdsc.timerservice.config.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class TimerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimerServiceApplication.class, args);
	}

}
