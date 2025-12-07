package com.userservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	@Bean
	@LoadBalanced // This tells Spring to use Eureka to resolve service names
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}