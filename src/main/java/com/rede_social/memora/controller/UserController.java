package com.rede_social.memora.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rede_social.memora.dto.UserLogin;
import com.rede_social.memora.model.posts.Posts;
import com.rede_social.memora.model.user.User;
import com.rede_social.memora.model.user.exceptions.DeleteUserIsForbiddenException;
import com.rede_social.memora.model.user.exceptions.UserNotFoundException;
import com.rede_social.memora.repository.UserRepository;
import com.rede_social.memora.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/all")
	public ResponseEntity <List<User>> getAll(){
		
		return ResponseEntity.ok(userRepository.findAll());
		
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {
		return userRepository.findById(id)
			.map(response -> ResponseEntity.ok(response))
			.orElseThrow(()->new UserNotFoundException("Usuário não encontrado."));
	}

	@GetMapping("/{user}")
	public ResponseEntity<User> getByUser(@PathVariable String user) {
		Optional<User> userOptional = userRepository.findByUser(user);
        
        if (userOptional.isPresent()) {
            User username = userOptional.get();
            return ResponseEntity.ok(username);
        } else {
			throw new UserNotFoundException("Usuário não encontrado.");}
		/*return userRepository.findByUser(user)
			.map(response -> ResponseEntity.ok(response))
			.orElseThrow(()->new UserNotFoundException("Usuário não encontrado."));*/
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserLogin> authenticateUser(@RequestBody Optional<UserLogin> userLogin){
		
		return userService.authenticateUser(userLogin)
				.map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
    

	@PostMapping("/signup")
	public ResponseEntity<User> postUser(@RequestBody @Valid User user) {

		return userService.createUser(user)
			.map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

	}

	@PutMapping("/update")
	public ResponseEntity<User> putUser(@Valid @RequestBody User user) {
		
		return userService.updateUser(user)
			.map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
			.orElseThrow(()->new UserNotFoundException("Usuário não encontrado."));
		
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

		Optional<User> userOptional = userRepository.findById(id);

		if (userOptional.isEmpty())

			throw new UserNotFoundException("Usuário não encontrado.");

		User user = userOptional.get();
        String currentUser = user.getUser();

        if (!currentUsername.equals(currentUser)) {
            throw new DeleteUserIsForbiddenException( "Você não tem permissão para excluir este usuário.");
        }

		userRepository.deleteById(id);

	}
}
