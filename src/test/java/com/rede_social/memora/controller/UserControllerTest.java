package com.rede_social.memora.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rede_social.memora.model.user.User;
import com.rede_social.memora.repository.UserRepository;
import com.rede_social.memora.service.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Autowired
	private TestRestTemplate testRestTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
	void start(){

		userRepository.deleteAll();

		userService.createUser(new User(0L, 
			"Root", "root", "rootroot", "root@root.com", "-"));

	}

	@Test
	@DisplayName("Cadastrar Um Usuário")
	public void mustCreateAUser() {

		HttpEntity<User> requisitionBody = new HttpEntity<User>(new User(0L, 
			"Paulo Antunes", "pauloAntunes", "13465278", "paulo_antunes@email.com.br", "-"));

		ResponseEntity<User> answerBody = testRestTemplate
			.exchange("/users/signup", HttpMethod.POST, requisitionBody, User.class);

		assertEquals(HttpStatus.CREATED, answerBody.getStatusCode());
	
	}

	@Test
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void mustNotDuplicateAUser() {

		userService.createUser(new User(0L, 
			"Maria da Silva", "maria", "13465278", "maria_silva@email.com.br", "-"));

		HttpEntity<User> requisitionBody = new HttpEntity<User>(new User(0L, 
			"Maria da Silva", "maria", "13465278", "maria_silva@email.com.br", "-"));

		ResponseEntity<User> answerBody = testRestTemplate
			.exchange("/users/signup", HttpMethod.POST, requisitionBody, User.class);

		assertEquals(HttpStatus.BAD_REQUEST, answerBody.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar um Usuário")
	public void mustUpdateAUser() {

		Optional<User> userCreated = userService.createUser(new User(0L, 
			"Juliana Andrews", "juliana", "juliana123", "juliana_andrews@email.com.br", "-"));

		User UserUpdated = new User(userCreated.get().getId(), 
			"Juliana Andrews Ramos", "julianaRamos", "juliana123" , "juliana_ramos@email.com.br", "-");
		
		HttpEntity<User> requisitionBody = new HttpEntity<User>(UserUpdated);

		ResponseEntity<User> answerBody = testRestTemplate
			.withBasicAuth("root", "rootroot")
			.exchange("/users/update", HttpMethod.PUT, requisitionBody, User.class);

		assertEquals(HttpStatus.OK, answerBody.getStatusCode());
		
	}

	@Test
	@DisplayName("Listar todos os Usuários")
	public void mustShowAllUsers() {

		userService.createUser(new User(0L, 
			"Sabrina Sanches", "sabrina", "sabrina123", "sabrina_sanches@email.com.br", "-"));
		
		userService.createUser(new User(0L, 
			"Ricardo Marques", "ricardo", "ricardo123", "ricardo_marques@email.com.br", "-"));

		ResponseEntity<String> answer = testRestTemplate
		.withBasicAuth("root", "rootroot")
			.exchange("/users/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, answer.getStatusCode());

	}
}
