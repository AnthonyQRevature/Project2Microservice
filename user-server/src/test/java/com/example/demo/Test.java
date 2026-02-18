package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={
	EurekaClientAutoConfiguration.class,
	EurekaDiscoveryClientConfiguration.class
})
@ComponentScan(basePackages="com.example")
public class Test {
    public static void main(String[] args) {
		SpringApplication.run(Test.class, args);
	}
}
