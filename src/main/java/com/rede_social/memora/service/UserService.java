package com.rede_social.memora.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rede_social.memora.dto.UserLogin;
import com.rede_social.memora.model.User;
import com.rede_social.memora.repository.UserRepository;
import com.rede_social.memora.security.JwtService;

@Service
public class UserService {

    @Autowired
	private UserRepository userRepository;

	@Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

	public Optional<User> createUser(User user) {

		if (userRepository.findByUsername(user.getUsername()).isPresent())
			return Optional.empty();

		user.setPassword(encryptPassword(user.getPassword()));

		return Optional.of(userRepository.save(user));
	
	}
    public Optional<User> updateUser(User user) {
		
		if(userRepository.findById(user.getId()).isPresent()) {

			Optional<User> searchUser = userRepository.findByUsername(user.getUsername());

			if ( (searchUser.isPresent()) && ( searchUser.get().getId() != user.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

			user.setPassword(encryptPassword(user.getPassword()));

			return Optional.ofNullable(userRepository.save(user));
			
		}

		return Optional.empty();
	
	}	

	public Optional<UserLogin> authenticateUser(Optional<UserLogin> userLogin) {

        // Gera o Objeto de autenticação
		var credentials = new UsernamePasswordAuthenticationToken(userLogin.get().getUsername(), userLogin.get().getPassword());
		
        // Autentica o user
		Authentication authentication = authenticationManager.authenticate(credentials);
        
        // Se a autenticação foi efetuada com sucesso
		if (authentication.isAuthenticated()) {

            // Busca os dados do usuário
			Optional<User> user = userRepository.findByUsername(userLogin.get().getUsername());

            // Se o usuário foi encontrado
			if (user.isPresent()) {

                // Preenche o Objeto userLogin com os dados encontrados 
			    userLogin.get().setId(user.get().getId());
                userLogin.get().setName(user.get().getName());
                userLogin.get().setEmail(user.get().getEmail());
                userLogin.get().setImage(user.get().getImage());
                userLogin.get().setToken(generateToken(userLogin.get().getUsername()));
                userLogin.get().setPassword("");

                // Retorna o Objeto preenchido
			   return userLogin;
			
			}

        } 
            
		return Optional.empty();

    }

	private String encryptPassword(String password) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(password);

	}

	private String generateToken(String user) {
		return "Bearer " + jwtService.generateToken(user);
	}

}
