package com.rede_social.memora.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rede_social.memora.model.user.User;
import com.rede_social.memora.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {

		Optional<User> userOptional = userRepository.findByUser(user);

		if (userOptional.isPresent())
			return new UserDetailsImpl(userOptional.get());
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			
	}

}
