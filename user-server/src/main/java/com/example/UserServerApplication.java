package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.example.clients.AuthClient;

@SpringBootApplication(exclude={
	EurekaClientAutoConfiguration.class,
	EurekaDiscoveryClientConfiguration.class
})
@EnableDiscoveryClient
@EnableFeignClients
public class UserServerApplication {
	@Bean
	public SecurityHelper securityHelper(AuthClient client)
	{
		return new SecurityHelper(client);
	}
}
