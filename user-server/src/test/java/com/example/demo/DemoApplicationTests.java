package com.example.demo;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.example.UserRole;
import com.example.clients.AuthClient;
import com.example.model.AuthResponse;
import com.example.model.UserResponse;
import com.example.model.UserResponse.ProfileResponse;
import com.example.repository.UserDao;
import com.example.repository.UserEntity;
import com.example.repository.UserProfileEntity;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude={
	EurekaClientAutoConfiguration.class,
	EurekaDiscoveryClientConfiguration.class,
	DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
@ExtendWith(MockitoExtension.class)
class DemoApplicationTests {

	@MockitoBean
	AuthClient auth;
	@MockitoBean
	UserDao dao;

	String myAuth = "jwttoken";
	static UserEntity myUser;
	static UserEntity sampleUser;

	@BeforeAll
	public static void initData()
	{
		myUser = new UserEntity("my@email", UserRole.user, 42, "myUsername", false);
		myUser.setUserProfile(new UserProfileEntity("address", "bio", 42, 100.0d, 200.0d, "pfp"));

		sampleUser = new UserEntity("email", UserRole.user, 1, "username", false);
		sampleUser.setUserProfile(new UserProfileEntity("address", "bio", 1, 3.0d, 200.0d, "pfp"));
	}

	@Test
	void contextLoads() {
	}

	void authServerAccepts()
	{
		when(auth.verifyToken(myAuth, myUser.getRole().value)).thenReturn(new AuthResponse(myAuth, true));
	}
	void daoReturnsUser()
	{
		when(dao.findById(myUser.getId())).thenReturn(Optional.of(myUser));
	}

	@Test // If AssertJ is on the classpath, you can use MockMvcTester
	void testWithMockMvcTester(@Autowired MockMvcTester mvc) {
		UserResponse expectedResponse = new UserResponse(
			"email", 
			1, 
			new ProfileResponse(
				"bio", 
				3.0d,
				200.0d,
				"pfp"
			), 
			1, 
			"username", 
			false
		);

		when(dao.findById(sampleUser.getId())).thenReturn(Optional.of(sampleUser));

		assertThat(mvc.get().uri("/users/1"))
				.hasStatusOk()
				.bodyJson().convertTo(UserResponse.class)
				.isEqualTo(expectedResponse);
	}

	
}
