package com.example.auth_server_test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.example.HasherUtil;
import com.example.UserRole;
import com.example.auth_server.repository.AuthDao;
import com.example.auth_server.repository.AuthEntity;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude={
	EurekaClientAutoConfiguration.class,
	EurekaDiscoveryClientConfiguration.class,
	DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
class AuthServerApplicationTests {

	@MockitoBean
	AuthDao dao;
	@MockitoBean
	HasherUtil hasher;

	@Test
	void contextLoads() {
	}

	@Test
	void registerNew(@Autowired MockMvcTester mvc)
	{
		when(dao.findByUsername("username")).thenReturn(Optional.empty());
		when(hasher.hashPassword("password")).thenReturn("hashed password");

		assertThat(mvc.post().uri("/auth/register")
				.content("{'username':'username','password':'password','email':'my@email'}".replace('\'','"'))
				.contentType(MediaType.APPLICATION_JSON))
			.hasStatusOk();

		verify(dao, times(1)).save(new AuthEntity(null, "hashed password", UserRole.user, "username"));
	}
}
