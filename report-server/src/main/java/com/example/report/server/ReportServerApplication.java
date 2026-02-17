package com.example.report.server;

import com.example.model.AuthResponse;
import com.example.model.RegisterCredentialsRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.example.SecurityHelper;
import com.example.clients.AuthClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example")
@ComponentScan
public class ReportServerApplication {
	/*
	public static void main(String[] args) {
		SpringApplication.run(ReportServerApplication.class, args);
	}
	 */

	@Bean
	public SecurityHelper securityHelper(AuthClient client)
	{
		return new SecurityHelper(client);
	}

}
