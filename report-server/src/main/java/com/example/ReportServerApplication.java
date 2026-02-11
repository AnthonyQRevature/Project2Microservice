package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude={
	EurekaClientAutoConfiguration.class,
	EurekaDiscoveryClientConfiguration.class
})
public class ReportServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportServerApplication.class, args);
	}

	/*
	 * Use the standard Mongo driver API to create a com.mongodb.client.MongoClient instance.
	 */
	public @Bean com.mongodb.client.MongoClient mongoClient() {
		return com.mongodb.client.MongoClients.create("mongodb://localhost:27017");
	}
}