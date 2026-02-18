package com.example.auth_server_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={
	EurekaClientAutoConfiguration.class,
	EurekaDiscoveryClientConfiguration.class
})
@ComponentScan(basePackages="com.example")
@EntityScan(basePackages="com.example.auth_server.repository")
public class Test {
    public static void main(String[] args) {
		SpringApplication.run(Test.class, args);
	}
}
