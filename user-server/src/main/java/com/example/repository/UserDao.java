package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Integer>{
    Optional<UserEntity> findUserByUsername(String username);
}
