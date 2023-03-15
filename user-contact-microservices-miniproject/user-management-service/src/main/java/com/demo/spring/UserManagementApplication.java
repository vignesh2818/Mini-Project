package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class UserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class,args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public RestTemplate restTemplate2() {
		return new RestTemplate();
	}
	
	@Bean
	public RestTemplate restTemplate3() {
		return new RestTemplate();
	}
	
	@Bean
	public RestTemplate restTemplate4() {
		return new RestTemplate();
	}
	
	@Bean
	public RestTemplate restTemplate5() {
		return new RestTemplate();
	}
	
	@Bean
	public RestTemplate restTemplate6() {
		return new RestTemplate();
	}
}
