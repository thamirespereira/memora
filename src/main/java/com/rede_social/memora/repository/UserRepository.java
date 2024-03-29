package com.rede_social.memora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rede_social.memora.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    public Optional<User> findByUsername(String username);
}
