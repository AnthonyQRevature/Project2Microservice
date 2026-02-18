package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.example.clients.AuthClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserServerApplication {
	@Bean
	public SecurityHelper securityHelper(AuthClient client)
	{
		return new SecurityHelper(client);
	}
	@Bean
	public DefaultPfp defaultPfp()
	{
		return new DefaultPfp();
	}
}
