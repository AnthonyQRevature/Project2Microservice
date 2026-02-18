package com.example.auth_server_test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.example.HasherUtil;
import com.example.Marshaller;
import com.example.TokenUtil;
import com.example.UserRole;
import com.example.auth_server.AuthServerApplication;
import com.example.auth_server.repository.AuthDao;
import com.example.auth_server.repository.AuthEntity;
import com.example.model.CredentialResponse;

import tools.jackson.databind.ObjectMapper;

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
class AuthServerApplicationTests {

	@MockitoBean
	AuthDao dao;
	@MockitoBean
	HasherUtil hasher;
	@MockitoBean
	TokenUtil tokenUtil;

	@Mock
	TokenUtil.Token mockToken;

	static ObjectMapper mapper = new ObjectMapper();

	void validToken(TokenUtil.Token mock) 
	{
		when(mock.isValid()).thenReturn(true);
	}
	void tokenToEntity(AuthDao mockDao, TokenUtil.Token mockToken, AuthEntity entry)
	{
		when(mockToken.getId()).thenReturn(entry.getId());
		when(mockDao.findById(entry.getId())).thenReturn(Optional.of(entry));
	}

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

	@Test
	void getAll(
		@Autowired MockMvcTester mvc,
		@Autowired Marshaller<CredentialResponse, AuthEntity> marshaller
	) throws Exception {
		String superUserToken = "super-user-token";
		AuthEntity superUser = new AuthEntity(65, null, UserRole.super_user, "super_user5");
		List<AuthEntity> entities = List.of(
			superUser, 
			new AuthEntity(123, null, UserRole.user, "my user")
		);
		List<CredentialResponse> expectedResponse = marshaller.convert(entities);

		when(tokenUtil.asToken(superUserToken)).thenReturn(mockToken);
		validToken(mockToken);
		tokenToEntity(dao, mockToken, superUser);
		when(dao.findAll()).thenReturn(entities);

		assertThat(mvc.get().uri("/auth/credentials").header("Authorization", superUserToken))
			.hasStatus(HttpStatus.OK)
			.bodyJson().convertTo(CredentialResponse[].class)
			.isEqualTo(expectedResponse.toArray());
	}
}
