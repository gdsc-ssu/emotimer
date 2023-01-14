package com.gdsc.timerservice.config.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

//	@Bean
//	public ObjectMapper objectMapper() {
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.registerModule(new JavaTimeModule());
//		mapper.registerModule(new JSR310Module());
//		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
//		return mapper;
//	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
