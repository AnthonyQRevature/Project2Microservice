package com.example.auth_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.UserEntity;

public interface AuthDao extends JpaRepository<UserEntity, Integer>{
    
    Optional<UserEntity> findByUsername(String username);
}
