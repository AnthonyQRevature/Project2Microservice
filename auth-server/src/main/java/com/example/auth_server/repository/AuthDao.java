package com.example.auth_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthDao extends JpaRepository<AuthEntity, Integer>{
    
    Optional<AuthEntity> findByUsername(String username);
}
