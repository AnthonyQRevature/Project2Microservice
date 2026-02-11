package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.example.clients.AuthClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServerApplication.class, args);
	}

	@Bean
	public SecurityHelper securityHelper(AuthClient client)
	{
		return new SecurityHelper(client);
	}
}
