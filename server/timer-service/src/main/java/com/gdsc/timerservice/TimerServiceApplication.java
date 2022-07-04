package com.gdsc.timerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.timerservice.config.properties.AppProperties;
import com.gdsc.timerservice.config.properties.OAuthProperties;
import com.gdsc.timerservice.oauth.model.OAuthVendor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties(value = {AppProperties.class, OAuthProperties.class})
public class TimerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimerServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Component
    @RequiredArgsConstructor
    static class MyApplicationRunner implements ApplicationRunner {
        private final OAuthProperties oAuthProperties;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            OAuthVendor.oAuthProperties = oAuthProperties;
        }
    }

}
