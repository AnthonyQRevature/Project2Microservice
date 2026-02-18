package com.example.auth_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.example.DefaultPfp;
import com.example.HasherUtil;
import com.example.Marshaller;
import com.example.TokenUtil;
import com.example.TokenUtil.TokenProperties;
import com.example.auth_server.repository.AuthEntity;
import com.example.model.CredentialResponse;

@SpringBootApplication
@EnableDiscoveryClient
@ConfigurationPropertiesScan(basePackages="com.example")
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}
	
	@Bean
	public TokenUtil tokenUtil(TokenProperties properties)
	{
		return new TokenUtil(properties);
	}
	@Bean
	public DefaultPfp defaultPfp()
	{
		return new DefaultPfp();
	}
	@Bean
	public HasherUtil hasher()
	{
		return new HasherUtil();
	}
	@Bean
	public Marshaller<CredentialResponse, AuthEntity> credentialMarshaller() {
		return new Marshaller<>((e) -> {
			return new CredentialResponse(e.getId(), e.getRole().value, e.getUsername());
		});
	}
}
