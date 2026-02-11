package com.example.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.UserRole;
import com.example.entity.UserEntity;
import com.example.model.UserResponse;
import com.example.model.UserUpdateRequest;
import com.example.repository.UserDao;

import jakarta.transaction.Transactional;

/*
 * a service class bean
 */
@Service
public class UserService {

    static List<UserResponse> toModel(List<UserEntity> entity)
    {
        return entity.stream().map(e -> toModel(e)).toList();
    }
    static Optional<UserResponse> toModel(Optional<UserEntity> entity)
    {
        if (entity.isPresent())
        {
            return Optional.of(toModel(entity.get()));
        }
        else
        {
            return Optional.empty();
        }
    }
    static UserResponse toModel(UserEntity entity)
    {
        return new UserResponse(entity);
    }

    UserDao dao;

    @Transactional
    public UserResponse patchUserEntity(Integer id, UserUpdateRequest body) throws AccountNotFoundException {
        //check existence
        UserEntity entity = dao.findById(id).orElseThrow(() -> new AccountNotFoundException());

        if (body.getEmail() != null) entity.setEmail(body.getEmail());
        if (body.getProfile() != null)
        {
            if (body.getProfile().getBio() != null) entity.getUserProfile().setBio(body.getProfile().getBio());
            if (body.getProfile().getLatitude() != null) entity.getUserProfile().setLatitude(body.getProfile().getLatitude());
            if (body.getProfile().getLongitude() != null) entity.getUserProfile().setLongitude(body.getProfile().getLongitude());
        }

        return toModel(entity);
    }
    
    public Optional<UserResponse> findByUsername(String username){
        return toModel(dao.findUserByUsername(username));
    }

    public boolean deleteUserById(int id){
        boolean exists = dao.existsById(id);
        if (exists)
            dao.deleteById(id);
        return exists;
    }

    public List<UserResponse> getAllUsers() {
        return toModel(dao.findAll());
    }

    public Optional<UserResponse> findById(Integer id) {
        return toModel(dao.findById(id));
    }

    @Transactional
    public boolean setRole(Integer userId, UserRole role)
    {
        try
        {
            UserEntity entity = dao.findById(userId).orElseThrow();
            entity.setRole(role);
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }

    }

    //achieves constructor injection
    @Autowired
    public UserService(
        UserDao dao
    ) {
        this.dao = dao;
    }
}
